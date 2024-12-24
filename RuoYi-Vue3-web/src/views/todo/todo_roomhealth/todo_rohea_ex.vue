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
                    name:'运行时间(日)',
                    nameTextStyle:{
                        lineHeight: 30, //标题行高
                        padding: [-30, 0, 0, -80],
                        verticalAlign: "top"
                    }
                },
                yAxis: {
                    scale: true,
                    name:'故障数量(件)',
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
                        data: [[1727, 105],[1534, 42]]
                    },
                    {
                        type:'scatter',
                        data: [[1612,51],[1675,59],[1595,49],[1570,63],[1558,53],
                                [1700,59],[1591,47],[1660,69],[1762,66],[1602,75],
                                [1725,55],[1709,54],[1729,62],[1534,42],[1600,50],
                                [1472,49],[1682,49],[1750,73],[1570,47],[1676,68],
                                [1595,50],[1750,82],[1668,57],[1765,87],[1702,72],
                                [1740,54],[1730,59],[1799,67],[1705,67],[1600,47],
                                [1544,46],[1620,55],[1765,83],[1600,54],[1520,45],
                                [1621,53],[1700,73],[1602,52],[1613,67],[1664,56],
                                [1689,62],[1638,58],[1676,54],[1600,50],[1613,60],
                                [1676,58],[1651,56],[1600,50],[1700,72],[1575,59],
                                [1676,61],[1607,69],[1632,55],[1524,46],[1575,54],
                                [1683,54],[1803,60],[1655,60],[1650,62],[1645,60],
                                [1560,52],[1600,74],[1630,62],[1657,73],[1610,80],
                                [1620,54],[1660,53],[1740,75],[1727,61],[1676,55],
                                [1511,48],[1645,52],[1635,50],[1520,59],[1690,62],
                                [1640,55],[1612,54],[1550,45],[1700,70],[1762,67],
                                [1700,69],[1625,58],[1703,64],[1641,71],[1695,52],
                                [1632,59],[1545,49],[1598,50],[1732,69],[1700,55],
                                [1614,63],[1690,58],[1662,58],[1594,45],[1625,52],
                                [1590,48],[1628,57],[1590,55],[1798,66],[1629,59],
                                [1610,53],[1511,73],[1682,53],[1689,69],[1732,58],
                                [1718,56],[1780,70],[1643,59],[1630,72],[1685,65],
                                [1668,56],[1727,105],[1635,51],[1694,63],[1678,59],
                                [1595,47],[1676,63],[1612,55],[1600,45],[1632,54],
                                [1622,50],[1613,60],[1495,44],[1575,58],[1632,56],
                                [1727,62],[1550,49],[1565,67],[1640,53],[1609,54],
                                [1628,58],[1670,59],[1600,54],[1600,43],[1689,60],
                                [1582,46],[1560,64],[1600,48],[1671,62],[1580,55],
                                [1676,57],[1560,54],[1621,59],[1734,52],[1598,53],
                                [1705,64],[1592,51],[1575,56],[1613,63],[1626,63],
                                [1600,59],[1689,56],[1651,64],[1626,50],[1651,72],
                                [1664,55],[1600,55],[1524,60],[1702,69],[1626,84],
                                [1702,55],[1588,55],[1727,69],[1676,76],[1626,61],
                                [1676,65],[1562,58],[1752,66],[1721,56],[1626,58],
                                [1600,55],[1651,59],[1829,81],[1664,70],[1651,56],
                                [1778,60],[1651,58],[1753,72],[1549,54],[1588,49],
                                [1727,75],[1689,55],[1613,57],[1676,55],[1651,65],
                                [1753,65],[1575,48],[1638,58],[1676,63],[1651,55],
                                [1651,62],[1689,56],[1626,53],[1645,63],[1765,73],
                                [1689,62],[1753,63],[1594,53],[1600,53],[1702,55],
                                [1626,70],[1676,54],[1626,54],[1607,55],[1600,59],
                                [1575,63],[1626,54],[1524,47],[1702,67],[1651,80],
                                [1727,70],[1651,60],[1702,63],[1702,54],[1702,59],
                                [1613,70],[1676,52],[1676,62],[1651,86],[1626,66],
                                [1524,67],[1689,63],[1702,73],[1752,62],[1752,57],
                                [1600,55],[1651,104],[1740,55],[1702,77],[1600,80],
                                [1676,64],[1676,72],[1676,61],[1549,58],[1626,81],
                                [1753,63],[1714,53],[1575,54],[1651,53],[1600,60],
                                [1740,73],[1626,61],[1740,55],[1626,63],[1613,60],
                                [1562,60],[1499,46],[1695,57],[1600,64],[1753,63],
                                [1695,67],[1600,75],[1727,68],[1626,61],[1575,76],
                                [1765,71],[1644,55],[1607,48],[1740,66],[1638,67]
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