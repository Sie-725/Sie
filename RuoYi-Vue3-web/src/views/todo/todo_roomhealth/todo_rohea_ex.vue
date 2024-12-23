<template>
    <div class="container">
        <div class="no1" ref="lineChart" style="width: 800px; height: 400px; background-color: #ffffff; padding: 20px; border-radius: 20px;">
        </div>
        <div class="no2" ref="lineChart2" style="width: 800px; height: 400px; background-color: #ffffff; padding: 20px; border-radius: 20px;">
        </div>
        <div class="no3">
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
                    v-hasPermi="['todo:todo_roomhealth:add']"
                    >新增</el-button>
                </el-col>
                <el-col :span="1.5">
                    <el-button
                    type="success"
                    plain
                    icon="Edit"
                    :disabled="single"
                    @click="handleUpdate"
                    v-hasPermi="['todo:todo_roomhealth:edit']"
                    >修改</el-button>
                </el-col>
                <el-col :span="1.5">
                    <el-button
                    type="danger"
                    plain
                    icon="Delete"
                    :disabled="multiple"
                    @click="handleDelete"
                    v-hasPermi="['todo:todo_roomhealth:remove']"
                    >删除</el-button>
                </el-col>
                <el-col :span="1.5">
                    <el-button
                    type="warning"
                    plain
                    icon="Download"
                    @click="handleExport"
                    v-hasPermi="['todo:todo_roomhealth:export']"
                    >导出</el-button>
                </el-col>
                <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
                </el-row>

                <el-table v-loading="loading" :data="todo_roomhealthList" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center" />
                <el-table-column label="名称" align="center" prop="name" />
                <el-table-column label="线路" align="center" prop="line" />
                <el-table-column label="地区" align="center" prop="area" />
                <el-table-column label="设备过保预警" align="center" class-name="small-padding fixed-width" >
                    <template  #default="scope">
                        <el-popover placement="right" title="" :width="200" trigger="click" :content="'设备年限评分为：'+form.life">
                                <template #reference>
                                    <div class="demo-progress">
                                        <el-progress 
                                        :percentage="parseInt((10.1-scope.row.life)/10.1*100)" 
                                        :color="customColors"
                                         @click="handleTimedate(scope.row)"/>
                                    </div>
                                </template>
                            </el-popover>
                    </template>
                </el-table-column>
                <el-table-column label="健康分" align="center" prop="total" />
                <el-table-column label="耗电量" align="center" prop="kwh" />
                <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                    <template #default="scope">
                        <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['todo:todo_roomhealth:edit']">修改</el-button>
                        <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['todo:todo_roomhealth:remove']">删除</el-button>
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
                <!-- 添加设备时间对话框 -->
                <el-dialog :title="title" v-model="open2" width="500px" append-to-body>
                    <el-form ref="todo_roomhealthRef" :model="form" :rules="rules" label-width="80px">
                        <el-form-item label="时长" prop="life">
                            <el-input v-model="form.life" placeholder="请输入设备时长平分" />
                        </el-form-item>
                    </el-form>
                </el-dialog>
                <!-- 添加或修改机房健康对话框 -->
                <el-dialog :title="title" v-model="open" width="500px" append-to-body>
                    <el-form ref="todo_roomhealthRef" :model="form" :rules="rules" label-width="80px">
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
                        <el-form-item label="JK" prop="jk">
                        <el-input v-model="form.jk" placeholder="请输入" />
                        </el-form-item>
                        <el-form-item label="地区" prop="area">
                        <el-input v-model="form.area" placeholder="请输入地区" />
                        </el-form-item>
                        <el-form-item label="等级" prop="lever">
                        <el-input v-model="form.lever" placeholder="请输入等级" />
                        </el-form-item>
                        <el-form-item label="温湿度" prop="temperature">
                        <el-input v-model="form.temperature" placeholder="请输入" />
                        </el-form-item>
                        <el-form-item label="硬件" prop="hardware">
                        <el-input v-model="form.hardware" placeholder="请输入" />
                        </el-form-item>
                        <el-form-item label="台账" prop="ledger">
                        <el-input v-model="form.ledger" placeholder="请输入" />
                        </el-form-item>
                        <el-form-item label="告警" prop="alarm">
                        <el-input v-model="form.alarm" placeholder="请输入" />
                        </el-form-item>
                        <el-form-item label="故障" prop="malfunction">
                        <el-input v-model="form.malfunction" placeholder="请输入" />
                        </el-form-item>
                        <el-form-item label="指标" prop="indicators">
                        <el-input v-model="form.indicators" placeholder="请输入指标值" />
                        </el-form-item>
                        <el-form-item label="时长" prop="life">
                        <el-input v-model="form.life" placeholder="请输入设备时长平分" />
                        </el-form-item>
                        <el-form-item label="权重" prop="weight">
                        <el-input v-model="form.weight" placeholder="请输入机房权重" />
                        </el-form-item>
                        <el-form-item label="健康分" prop="total">
                        <el-input v-model="form.total" placeholder="请输入健康分" />
                        </el-form-item>
                        <el-form-item label="耗电量" prop="kwh">
                        <el-input v-model="form.kwh" placeholder="请输入耗电量" />
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
 <script setup  name="Todo_roomhealth">
    import { listTodo_roomhealth, getTodo_roomhealth, delTodo_roomhealth, addTodo_roomhealth, updateTodo_roomhealth } from "@/api/todo/todo_roomhealth";

    const customColors = [
    //{ color: '#f56c6c', percentage: 20 },
    //{ color: '#e6a23c', percentage: 40 },
    { color: '#5cb87a', percentage: 70 },
    { color: '#e6a23c', percentage: 90 },
    { color: '#B50700', percentage: 100 },
    ];

    const { proxy } = getCurrentInstance();

    const todo_roomhealthList = ref([]);
    const open = ref(false);
    const open2 = ref(false);
    const loading = ref(true);
    const showSearch = ref(true);
    const ids = ref([]);
    const single = ref(true);
    const multiple = ref(true);
    const total = ref(0);
    const title = ref("");

    let mytime=0;

    const data = reactive({
    form: {},
    queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        line: null,
        area: null,
    },
    rules: {
    }
    });

    const { queryParams, form, rules } = toRefs(data);

    /** 查询机房健康列表 */
    function getList() {
    loading.value = true;
    listTodo_roomhealth(queryParams.value).then(response => {
        todo_roomhealthList.value = response.rows;
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
        total: null,
        kwh: null
    };

    //this.Chart2option.series[0].data=this.lin2data

    proxy.resetForm("todo_roomhealthRef");
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
    ids.value = selection.map(item => item.name);
    single.value = selection.length != 1;
    multiple.value = !selection.length;
    }

    /** 新增按钮操作 */
    function handleAdd() {
    reset();
    open.value = true;
    title.value = "添加机房健康";
    }

    /** 修改按钮操作 */
    function handleUpdate(row) {
    reset();
    const _name = row.name || ids.value
    getTodo_roomhealth(_name).then(response => {
        form.value = response.data;
        open.value = true;
        title.value = "修改机房健康";
    });
    }

    /** 查询时间操作 */
    function handleTimedate(row) {
    const _name = row.name || ids.value
    getTodo_roomhealth(_name).then(response => {
        form.value = response.data;
        mytime = (10.1-form.value.life)/10.1*100 ;
        console.log(mytime);
        //open2.value = true;
        //title.value = "修改机房健康";
    });
    }

    /** 提交按钮 */
    function submitForm() {
    proxy.$refs["todo_roomhealthRef"].validate(valid => {
        if (valid) {
        if (form.value.name != null) {
            updateTodo_roomhealth(form.value).then(response => {
            proxy.$modal.msgSuccess("修改成功");
            open.value = false;
            getList();
            });
        } else {
            addTodo_roomhealth(form.value).then(response => {
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
    const _names = row.name || ids.value;
    proxy.$modal.confirm('是否确认删除机房健康编号为"' + _names + '"的数据项？').then(function() {
        return delTodo_roomhealth(_names);
    }).then(() => {
        getList();
        proxy.$modal.msgSuccess("删除成功");
    }).catch(() => {});
    }

    /** 导出按钮操作 */
    function handleExport() {
    proxy.download('todo/todo_roomhealth/export', {
        ...queryParams.value
    }, `todo_roomhealth_${new Date().getTime()}.xlsx`)
    }

    getList();
</script>
<script>
    import * as echarts from 'echarts'
    import health_data from '@/api/todo/todo_health_data.json';
    /* import Todo_roomhealth from '../todo_roomhealth/todo_rohea_ex.vue' */
    export default {
/*          components: {
            Todo_roomhealth
        },  */
        data(){
            return{
                //lin2data:null,
                //Chart2option: null,
            }
            
        },
        computed:{
        },
        mounted() {
            console.log(this.todo_roomhealthList)
            this.lineChart = echarts.init(this.$refs.lineChart)
            this.lineChart.setOption({
                title: {
                    text: '设备故障分布'
                },
                // 提示框
                tooltip: {
                    trigger: 'axis',
/*                     formatter:function(params){
                        console.log(params[0].value[0])
                        return params; 
                    } */
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
                    scale: true,
                    //name:'运行时间'
                },
                yAxis: {
                    scale: true,
                    name:'故障数量'
                },
                series: [
                    {
                        type: 'effectScatter',
                        symbolSize: 20,
                        data: [[172.7, 105.2],[153.4, 42]]
                    },
                    {
                        type:'scatter',
                        data: [[161.2, 51.6], [167.5, 59.0], [159.5, 49.2], [157.0, 63.0], [155.8, 53.6],
                                [170.0, 59.0], [159.1, 47.6], [166.0, 69.8], [176.2, 66.8], [160.2, 75.2],
                                [172.5, 55.2], [170.9, 54.2], [172.9, 62.5], [153.4, 42.0], [160.0, 50.0],
                                [147.2, 49.8], [168.2, 49.2], [175.0, 73.2], [157.0, 47.8], [167.6, 68.8],
                                [159.5, 50.6], [175.0, 82.5], [166.8, 57.2], [176.5, 87.8], [170.2, 72.8],
                                [174.0, 54.5], [173.0, 59.8], [179.9, 67.3], [170.5, 67.8], [160.0, 47.0],
                                [154.4, 46.2], [162.0, 55.0], [176.5, 83.0], [160.0, 54.4], [152.0, 45.8],
                                [162.1, 53.6], [170.0, 73.2], [160.2, 52.1], [161.3, 67.9], [166.4, 56.6],
                                [168.9, 62.3], [163.8, 58.5], [167.6, 54.5], [160.0, 50.2], [161.3, 60.3],
                                [167.6, 58.3], [165.1, 56.2], [160.0, 50.2], [170.0, 72.9], [157.5, 59.8],
                                [167.6, 61.0], [160.7, 69.1], [163.2, 55.9], [152.4, 46.5], [157.5, 54.3],
                                [168.3, 54.8], [180.3, 60.7], [165.5, 60.0], [165.0, 62.0], [164.5, 60.3],
                                [156.0, 52.7], [160.0, 74.3], [163.0, 62.0], [165.7, 73.1], [161.0, 80.0],
                                [162.0, 54.7], [166.0, 53.2], [174.0, 75.7], [172.7, 61.1], [167.6, 55.7],
                                [151.1, 48.7], [164.5, 52.3], [163.5, 50.0], [152.0, 59.3], [169.0, 62.5],
                                [164.0, 55.7], [161.2, 54.8], [155.0, 45.9], [170.0, 70.6], [176.2, 67.2],
                                [170.0, 69.4], [162.5, 58.2], [170.3, 64.8], [164.1, 71.6], [169.5, 52.8],
                                [163.2, 59.8], [154.5, 49.0], [159.8, 50.0], [173.2, 69.2], [170.0, 55.9],
                                [161.4, 63.4], [169.0, 58.2], [166.2, 58.6], [159.4, 45.7], [162.5, 52.2],
                                [159.0, 48.6], [162.8, 57.8], [159.0, 55.6], [179.8, 66.8], [162.9, 59.4],
                                [161.0, 53.6], [151.1, 73.2], [168.2, 53.4], [168.9, 69.0], [173.2, 58.4],
                                [171.8, 56.2], [178.0, 70.6], [164.3, 59.8], [163.0, 72.0], [168.5, 65.2],
                                [166.8, 56.6], [172.7, 105.2], [163.5, 51.8], [169.4, 63.4], [167.8, 59.0],
                                [159.5, 47.6], [167.6, 63.0], [161.2, 55.2], [160.0, 45.0], [163.2, 54.0],
                                [162.2, 50.2], [161.3, 60.2], [149.5, 44.8], [157.5, 58.8], [163.2, 56.4],
                                [172.7, 62.0], [155.0, 49.2], [156.5, 67.2], [164.0, 53.8], [160.9, 54.4],
                                [162.8, 58.0], [167.0, 59.8], [160.0, 54.8], [160.0, 43.2], [168.9, 60.5],
                                [158.2, 46.4], [156.0, 64.4], [160.0, 48.8], [167.1, 62.2], [158.0, 55.5],
                                [167.6, 57.8], [156.0, 54.6], [162.1, 59.2], [173.4, 52.7], [159.8, 53.2],
                                [170.5, 64.5], [159.2, 51.8], [157.5, 56.0], [161.3, 63.6], [162.6, 63.2],
                                [160.0, 59.5], [168.9, 56.8], [165.1, 64.1], [162.6, 50.0], [165.1, 72.3],
                                [166.4, 55.0], [160.0, 55.9], [152.4, 60.4], [170.2, 69.1], [162.6, 84.5],
                                [170.2, 55.9], [158.8, 55.5], [172.7, 69.5], [167.6, 76.4], [162.6, 61.4],
                                [167.6, 65.9], [156.2, 58.6], [175.2, 66.8], [172.1, 56.6], [162.6, 58.6],
                                [160.0, 55.9], [165.1, 59.1], [182.9, 81.8], [166.4, 70.7], [165.1, 56.8],
                                [177.8, 60.0], [165.1, 58.2], [175.3, 72.7], [154.9, 54.1], [158.8, 49.1],
                                [172.7, 75.9], [168.9, 55.0], [161.3, 57.3], [167.6, 55.0], [165.1, 65.5],
                                [175.3, 65.5], [157.5, 48.6], [163.8, 58.6], [167.6, 63.6], [165.1, 55.2],
                                [165.1, 62.7], [168.9, 56.6], [162.6, 53.9], [164.5, 63.2], [176.5, 73.6],
                                [168.9, 62.0], [175.3, 63.6], [159.4, 53.2], [160.0, 53.4], [170.2, 55.0],
                                [162.6, 70.5], [167.6, 54.5], [162.6, 54.5], [160.7, 55.9], [160.0, 59.0],
                                [157.5, 63.6], [162.6, 54.5], [152.4, 47.3], [170.2, 67.7], [165.1, 80.9],
                                [172.7, 70.5], [165.1, 60.9], [170.2, 63.6], [170.2, 54.5], [170.2, 59.1],
                                [161.3, 70.5], [167.6, 52.7], [167.6, 62.7], [165.1, 86.3], [162.6, 66.4],
                                [152.4, 67.3], [168.9, 63.0], [170.2, 73.6], [175.2, 62.3], [175.2, 57.7],
                                [160.0, 55.4], [165.1, 104.1], [174.0, 55.5], [170.2, 77.3], [160.0, 80.5],
                                [167.6, 64.5], [167.6, 72.3], [167.6, 61.4], [154.9, 58.2], [162.6, 81.8],
                                [175.3, 63.6], [171.4, 53.4], [157.5, 54.5], [165.1, 53.6], [160.0, 60.0],
                                [174.0, 73.6], [162.6, 61.4], [174.0, 55.5], [162.6, 63.6], [161.3, 60.9],
                                [156.2, 60.0], [149.9, 46.8], [169.5, 57.3], [160.0, 64.1], [175.3, 63.6],
                                [169.5, 67.3], [160.0, 75.5], [172.7, 68.2], [162.6, 61.4], [157.5, 76.8],
                                [176.5, 71.8], [164.4, 55.5], [160.7, 48.6], [174.0, 66.4], [163.8, 67.3]
                            ]
                    }
                ]
            })

            this.lineChart2 = echarts.init(this.$refs.lineChart2)
            this.Chart2option={
                title: {
                    text: '机房健康度分析'
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        // Use axis to trigger tooltip
                        type: 'shadow' // 'shadow' as default; can also be 'line' or 'shadow'
                    }
                },
                legend: {},
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: {
                    type: 'value'
                },
                yAxis: {
                    type: 'category',
                    data: ['2021.10', '2022.03', '2022.10', '2023.03', '2023.10', '2024.03', '2024.10']
                },
                series: [
                    {
                    name: '温湿度',
                    type: 'bar',
                    stack: 'total',
                    label: {
                        show: true
                    },
                    emphasis: {
                        focus: 'series'
                    },
                    data: [4, 4, 3, 5, 4, 4, 5]
                    },
                    {
                    name: '硬件',
                    type: 'bar',
                    stack: 'total',
                    label: {
                        show: true
                    },
                    emphasis: {
                        focus: 'series'
                    },
                    data: [7, 8, 8, 8, 9, 9, 10]
                    },
                    {
                    name: '台账',
                    type: 'bar',
                    stack: 'total',
                    label: {
                        show: true
                    },
                    emphasis: {
                        focus: 'series'
                    },
                    data: [8, 7, 8, 8, 8, 8, 10]
                    },
                    {
                    name: '设备',
                    type: 'bar',
                    stack: 'total',
                    label: {
                        show: true
                    },
                    emphasis: {
                        focus: 'series'
                    },
                    data: [49.5, 51.3, 52.2, 53.1, 53.1, 54, 54.9]
                    }
                ]
            },
            this.lineChart2.setOption(this.Chart2option)
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

    .demo-progress .el-progress--line {
        margin-bottom: 15px;
        max-width: 600px;
    }
</style>