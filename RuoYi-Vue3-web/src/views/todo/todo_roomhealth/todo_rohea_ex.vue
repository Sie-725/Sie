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
            this.lineChart = echarts.init(this.$refs.lineChart)
            this.lineChart.setOption({
                title: {
                    text: '评分分布'
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
                    name:'健康分',
                    nameTextStyle:{
                        lineHeight: 30, //标题行高
                        padding: [-30, 0, 0, -50],
                        verticalAlign: "top"
                    }
                },
                yAxis: {
                    scale: true,
                    name:'机房数',
                    nameTextStyle:{
                        lineHeight: 30, //标题行高
                        padding: [-10, 0, 0, 80],
                        verticalAlign: "right"
                    }
                },
                series: [
                    {
                        type: 'effectScatter',
                        symbolSize: 20,
                        data: []
                    },
                    {
                        type:'scatter',
                        data: [[79.9,2],	[84,4],	[85.3,1],	[73.6,1],	[92.7,1],	[86.6,1],
                                [73.2,1],	[79.2,2],	[82.1,7],	[77.5,1],	[90,1],	[74.9,2],
                                [77.7,4],	[85.9,4],	[77.8,1],	[73.9,3],	[90.2,1],	[75.5,2],
                                [74.1,1],	[86.8,3],	[83.1,3],	[81.4,2],	[87.1,1],	[78.2,2],
                                [72.9,4],	[89.4,1],	[83.9,3],	[83.3,2],	[87.3,1],	[87.6,2],
                                [76.7,1],	[69.5,1],	[73.7,1],	[72.1,1],	[91.7,1],	[72.7,2],
                                [75.7,10],	[78.5,4],	[77.2,1],	[70.1,2],	[85.4,1],	[81.3,3],
                                [92.1,1],	[72.8,1],	[73,2],	[76.5,1],	[81.5,1],	[75.1,1],
                                [85.6,1],	[78.1,1],	[78.4,3],	[74,2],	[89,1],	[77.4,1],
                                [89.8,1],	[75,3],	[80.5,3],	[82.9,1],	[88.7,2],	[82.4,1],
                                [86.4,2],	[76,3],	[80.3,4],	[86,1],	[77.6,3],	[79.7,1],
                                [84.9,2],	[84.6,1],	[80.2,2],	[81.1,2],	[79.6,3],	[82.5,1],
                                [83,4],	[80.4,1],	[79.4,5],	[81.6,1],	[84.7,2],	[85.7,1],
                                [83.6,1],	[79.3,3],	[87,1],	[83.2,2],	[73.8,3],	[87.8,1],
                                [85.1,3],	[74.7,1],	[81.2,3],	[80.7,1],	[82,3],	[75.8,1],
                                [82.2,1],	[88.5,1],	[78.8,1],	[76.9,1],	[86.7,2],	[71.2,1],
                                [83.5,1],	[74.6,1],	[75.6,3],	[84.4,1],	[80,1],	[81,1],
                                [80.9,1],	[83.8,2],	[79.5,4],	[70.9,1],	[74.8,4],	[72,1],
                                [87.5,1],	[78.3,4],	[70,2],	[76.1,1],	[78.7,1],	[81.9,1],
                                [81.7,1],	[84.1,4],	[75.4,2],	[73.1,1],	[81.8,1],	[71.9,1],
                                [84.3,1],	[82.3,3],	[85,2],	[74.5,1],	[83.7,1],	[84.8,1]
                            ]
                    }
                ]
            })

            this.lineChart2 = echarts.init(this.$refs.lineChart2)
            this.Chart2option={
                title: {
                    text: '平均评分占比',
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    top: '5%',
                    left: 'center'
                },
                series: [
                    {
                    name: '',
                    type: 'pie',
                    radius: ['40%', '70%'],
                    avoidLabelOverlap: false,
                    padAngle: 5,
                    itemStyle: {
                        borderRadius: 10
                    },
                    label: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        label: {
                        show: true,
                        fontSize: 20,
                        fontWeight: 'bold'
                        }
                    },
                    labelLine: {
                        show: false
                    },
                    data: [
                        { value: 4.6, name: '机房硬件' },
                        { value: 18, name: '设备状态' },
                        { value: 6.7, name: '设备年限' },
                        { value: 8.6, name: '台账管理' },
                        { value: 18.6, name: '业务告警' }
                    ],
                    }
                ]
                };
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