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
        <div class="no3">
            <div class="app-container">
                <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
                <el-form-item label="设备" prop="name">
                    <el-input
                    v-model="queryParams.name"
                    placeholder="请输入设备"
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
                <el-form-item label="区域" prop="area">
                    <el-input
                    v-model="queryParams.area"
                    placeholder="请输入区域"
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
                <el-table-column label="序号" align="center" prop="id" />
                <el-table-column label="设备" align="center" prop="name" />
                <el-table-column label="经度" align="center" prop="gisLo" />
                <el-table-column label="纬度" align="center" prop="gisLa" />
                <el-table-column label="线路" align="center" prop="line" />
                <el-table-column label="区域" align="center" prop="area" />
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

                <!-- 添加或修改机房信息对话框 -->
                <el-dialog :title="title" v-model="open" width="500px" append-to-body>
                <el-form ref="todo_roomRef" :model="form" :rules="rules" label-width="80px">
                    <el-form-item label="设备" prop="name">
                    <el-input v-model="form.name" placeholder="请输入设备" />
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
                    <el-form-item label="区域" prop="area">
                    <el-input v-model="form.area" placeholder="请输入区域" />
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
        </div>
    </div>
</template>
 <script setup  name="Todo_room">
    import { listTodo_room, getTodo_room, delTodo_room, addTodo_room, updateTodo_room } from "@/api/todo/todo_room";

    const { proxy } = getCurrentInstance();

    const todo_roomList = ref([]);
    const open = ref(false);
    const loading = ref(true);
    const showSearch = ref(true);
    const ids = ref([]);
    const single = ref(true);
    const multiple = ref(true);
    const total = ref(0);
    const title = ref("");

    const data = reactive({
    form: {},
    queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        line: null,
        area: null,
        lever: null,
    },
    rules: {
        name: [
        { required: true, message: "设备不能为空", trigger: "blur" }
        ],
        line: [
        { required: true, message: "线路不能为空", trigger: "blur" }
        ],
    }
    });

    const { queryParams, form, rules } = toRefs(data);

    /** 查询机房信息列表 */
    function getList() {
    loading.value = true;
    listTodo_room(queryParams.value).then(response => {
        todo_roomList.value = response.rows;
        total.value = response.total;
        loading.value = false;
    });
    }

    // 取消按钮
    function cancel() {
    open.value = false;
    reset();
    }

    // 表单重置
    function reset() {
    form.value = {
        id: null,
        name: null,
        gisLo: null,
        gisLa: null,
        line: null,
        jk: null,
        area: null,
        lever: null,
        temperature: null,
        hardware: null,
        ledger: null,
        alarm: null,
        malfunction: null,
        indicators: null,
        life: null,
        weight: null,
        total: null
    };
    proxy.resetForm("todo_roomRef");
    }

    /** 搜索按钮操作 */
    function handleQuery() {
    queryParams.value.pageNum = 1;
    getList();
    }

    /** 重置按钮操作 */
    function resetQuery() {
    proxy.resetForm("queryRef");
    handleQuery();
    }

    // 多选框选中数据
    function handleSelectionChange(selection) {
    ids.value = selection.map(item => item.id);
    single.value = selection.length != 1;
    multiple.value = !selection.length;
    }

    /** 新增按钮操作 */
    function handleAdd() {
    reset();
    open.value = true;
    title.value = "添加机房信息";
    }

    /** 修改按钮操作 */
    function handleUpdate(row) {
    reset();
    const _id = row.id || ids.value
    getTodo_room(_id).then(response => {
        form.value = response.data;
        open.value = true;
        title.value = "修改机房信息";
    });
    }

    /** 提交按钮 */
    function submitForm() {
    proxy.$refs["todo_roomRef"].validate(valid => {
        if (valid) {
        if (form.value.id != null) {
            updateTodo_room(form.value).then(response => {
            proxy.$modal.msgSuccess("修改成功");
            open.value = false;
            getList();
            });
        } else {
            addTodo_room(form.value).then(response => {
            proxy.$modal.msgSuccess("新增成功");
            open.value = false;
            getList();
            });
        }
        }
    });
    }

    /** 删除按钮操作 */
    function handleDelete(row) {
    const _ids = row.id || ids.value;
    proxy.$modal.confirm('是否确认删除机房信息编号为"' + _ids + '"的数据项？').then(function() {
        return delTodo_room(_ids);
    }).then(() => {
        getList();
        proxy.$modal.msgSuccess("删除成功");
    }).catch(() => {});
    }

    /** 导出按钮操作 */
    function handleExport() {
    proxy.download('todo/todo_room/export', {
        ...queryParams.value
    }, `todo_room_${new Date().getTime()}.xlsx`)
    }

    getList();
</script>
<script>
    import * as echarts from 'echarts'
    import Todo_room from '../todo_room/index.vue'
    export default {
        components: {
            Todo_room
        },
        data(){
            return{
                lin2data:null,
                line2option: null,
            }
            
        },
        computed:{
        },
        methods:{
            chartUpdate(row) {
                this.lin2data=[row.line],
                console.log(row)
                console.log(this.lin2data)
                //this.line2option.series[0].data=this.lin2data
                //this.lineChart2.setOption(this.line2option,true)
            },
        },
        mounted() {
            this.lineChart = echarts.init(this.$refs.lineChart)
            this.lineChart.setOption({
                title: {
                    text: '机房地区分布'
                },
                // 提示框
                tooltip: {
                    trigger: 'axis'
                },
                grid: {
                    left: '3%',
                    right: '3%',
                    bottom: '3%',
                    containLabel: true
                },
                // 工具栏
                toolbox: {
                    feature: {
                        saveAsImage: {
                            type: 'png'
                        },
                        magicType: {
                            type: ['line', 'bar', 'stack']
                        }
                    }
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: ['','济南','淄博','潍坊','青岛','聊城','莱芜','烟台','菏泽','泰安','枣庄','临沂','日照','德州','']
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        //name: '不佳',
                        type: 'bar',
                        stack: '总量',
                        // smooth: true,
                        data: [0,16,5,36,30,13,29,18,23,18,27,22,2,1,0]
                    }
                ]
            })

            this.lineChart2 = echarts.init(this.$refs.lineChart2)
            this.lineChart2.setOption({
                title: {
                    text: '机房线路分布'
                },
                // 提示框
                tooltip: {
                    trigger: 'axis'
                },
                grid: {
                    left: '3%',
                    right: '3%',
                    bottom: '3%',
                    containLabel: true
                },
                // 工具栏
                toolbox: {
                    feature: {
                        saveAsImage: {
                            type: 'png'
                        },
                        magicType: {
                            type: ['line', 'bar', 'stack']
                        }
                    }
                },
                xAxis: {
                    type: 'category',
                    axisLabel: {
                        rotate: 60,
                    },
                    boundaryGap: false,
                    data: ['','胶济线','京九线','辛泰线','济青高','潍莱高铁','潍烟高铁',
                    '莱荣高铁','青盐铁路','日兰高铁','京沪高','石济客专','济莱高铁',
                    '济郑高铁','瓦日线','京沪线','德大线','大莱荣线','海青铁路','新兖石',
                    '枣临线','张东线','小沾线','磁东线','东平东莱','胶新线','邯济铁路','']
                },
                yAxis: {
                    type: 'value',
                    
                },
                
                series: [
                    {
                        //name: '不佳',
                        type: 'bar',
                        stack: '总量',
                        barCategoryGap:'0%',
                        barGap:'0%',
                        barWidth:'60%',
                        
                        // smooth: true,
                        data: [0,18,10,10,10,3,8,18,5,14,8,3,5,4,11,23,13,8,4,15,8,5,7,5,5,15,5,0]
                    }
                ]
            })
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