<template>
    <div class="container">
        <div class="no1" ref="lineChart" style="width: 800px; height: 400px; background-color: #ffffff; padding: 20px; border-radius: 20px;">
        </div>
        <div class="no2" ref="lineChart2" style="width: 800px; height: 400px; background-color: #ffffff; padding: 20px; border-radius: 20px;">
        </div>
<!--         <div class="no4" ref="lineChart2" style="width: 800px; height: 400px; background-color: #ffffff; padding: 20px; border-radius: 20px;">
        </div> -->
<!--         <div class="no5" ref="lineChart3" style="width: 700px; height: 400px; background-color: #ffffff; padding: 20px; border-radius: 20px;">
        </div> -->
<!--         <div class="no3">
            <div class="app-container">
                <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
                    <el-form-item label="名称" prop="name">
                        <el-input
                        v-model="queryParams.name"
                        placeholder="请输入名称"
                        clearable
                        @keyup.enter="handleQuery"
                        />
                    </el-form-item>
                    <el-form-item label="线路" prop="line">
                        <el-input
                        v-model="queryParams.line"
                        placeholder="请输入线路"
                        clearable
                        @keyup.enter="handleQuery"
                        />
                    </el-form-item>
                    <el-form-item label="地区" prop="area">
                        <el-input
                        v-model="queryParams.area"
                        placeholder="请输入地区"
                        clearable
                        @keyup.enter="handleQuery"
                        />
                    </el-form-item>
                    <el-form-item label="等级" prop="lever">
                        <el-input
                        v-model="queryParams.lever"
                        placeholder="请输入等级"
                        clearable
                        @keyup.enter="handleQuery"
                        />
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
                        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
                    </el-form-item>
                </el-form>

                <el-row :gutter="10" class="mb8">
                    <el-col :span="1.5">
                        <div>商品总价: {{ totalPrice }}</div>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button
                        type="primary"
                        plain
                        icon="Plus"
                        @click="handleAdd"
                        v-hasPermi="['todo:todo_room:add']"
                        >新增</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button
                        type="success"
                        plain
                        icon="Edit"
                        :disabled="single"
                        @click="handleUpdate"
                        v-hasPermi="['todo:todo_room:edit']"
                        >修改</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button
                        type="danger"
                        plain
                        icon="Delete"
                        :disabled="multiple"
                        @click="handleDelete"
                        v-hasPermi="['todo:todo_room:remove']"
                        >删除</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button
                        type="warning"
                        plain
                        icon="Download"
                        @click="handleExport"
                        v-hasPermi="['todo:todo_room:export']"
                        >导出</el-button>
                    </el-col>
                    <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
                </el-row>
                <el-table v-loading="loading" :data="todo_roomList" @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="55" align="center" />
                    <el-table-column label="名称" align="center" prop="name" />
                    <el-table-column label="经度" align="center" prop="gisLo" />
                    <el-table-column label="纬度" align="center" prop="gisLa" />
                    <el-table-column label="线路" align="center" prop="line" />
                    <el-table-column label="地区" align="center" prop="area" />
                    <el-table-column label="等级" align="center" prop="lever" />
                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                        <template #default="scope">
                            <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['todo:todo_room:edit']">修改</el-button>
                            <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['todo:todo_room:remove']">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                
                <pagination
                    v-show="total>0"
                    :total="total"
                    v-model:page="queryParams.pageNum"
                    v-model:limit="queryParams.pageSize"
                    @pagination="getList"
                />
                
                <el-dialog :title="title" v-model="open" width="500px" append-to-body>
                    <el-form ref="todo_roomRef" :model="form" :rules="rules" label-width="80px">
                        <el-form-item label="名称" prop="name">
                        <el-input v-model="form.name" placeholder="请输入名称" />
                        </el-form-item>
                        <el-form-item label="经度" prop="gisLo">
                        <el-input v-model="form.gisLo" placeholder="请输入经度" />
                        </el-form-item>
                        <el-form-item label="纬度" prop="gisLa">
                        <el-input v-model="form.gisLa" placeholder="请输入纬度" />
                        </el-form-item>
                        <el-form-item label="线路" prop="line">
                        <el-input v-model="form.line" placeholder="请输入线路" />
                        </el-form-item>
                        <el-form-item label="地区" prop="area">
                        <el-input v-model="form.area" placeholder="请输入地区" />
                        </el-form-item>
                        <el-form-item label="等级" prop="lever">
                        <el-input v-model="form.lever" placeholder="请输入等级" />
                        </el-form-item>
                    </el-form>
                    <template #footer>
                        <div class="dialog-footer">
                        <el-button type="primary" @click="submitForm">确 定</el-button>
                        <el-button @click="cancel">取 消</el-button>
                        </div>
                    </template>
                </el-dialog>
            </div>
        </div> -->
    </div>
</template>
<script setup  name="sdchart">
  
</script>
<script>
    import * as echarts from 'echarts';
    import 'echarts-gl';
    var ROOT_PATH = 'https://echarts.apache.org/examples';
    var option;
    export default {
        data(){
            return{
            }
        },
        computed:{
        },
        methods:{
        },
        mounted() {
          this.lineChart = echarts.init(this.$refs.lineChart)
          option={
            backgroundColor: '#000',
            globe: {
              baseTexture: ROOT_PATH + '/data-gl/asset/world.topo.bathy.200401.jpg',
              heightTexture: ROOT_PATH + '/data-gl/asset/world.topo.bathy.200401.jpg',
              displacementScale: 0.04,
              shading: 'realistic',
              environment: ROOT_PATH + '/data-gl/asset/starfield.jpg',
              realisticMaterial: {
                roughness: 0.9
              },
              postEffect: {
                enable: true
              },
              light: {
                main: {
                  intensity: 5,
                  shadow: true
                },
                ambientCubemap: {
                  texture: ROOT_PATH + '/data-gl/asset/pisa.hdr',
                  diffuseIntensity: 0.2
                }
              }
            }
          };
          option && this.lineChart.setOption(option);
          console.log(this.lineChart.option)
        }
    }
</script>
<style>
    .container {  display: grid;
    grid-template-columns: 1fr 1fr 1fr 1fr;
    grid-template-rows: 200x 200x 200x;
    gap: 0px 0px;
    grid-auto-flow: row;
    grid-template-areas:
        "no1 no1 no2 no2"
        /* "no4 no4 no5 no5" */  
        "no3 no3 no3 no3"
    }

    .no1 { grid-area: no1; }

    .no2 { grid-area: no2; }

    .no3 { grid-area: no3; }

    .no4 { grid-area: no4; }

    .no5 { grid-area: no5; }
</style>