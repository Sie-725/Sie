<template>
    <div class="container">
        <div class="no1" ref="lineChart" style="width: 800px; height: 400px; background-color: #ffffff; padding: 20px; border-radius: 20px;">
        </div>
        <div class="no2" ref="myChart" style="width: 400px; height: 400px; background-color: #ffffff; padding: 20px; border-radius: 20px;">
        </div>
        <div class="no4" ref="lineChart2" style="width: 700px; height: 400px; background-color: #ffffff; padding: 20px; border-radius: 20px;">
        </div>
        <div class="no5" ref="lineChart3" style="width: 700px; height: 400px; background-color: #ffffff; padding: 20px; border-radius: 20px;">
        </div>
        <div class="no3">
            <div class="app-container">
                <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
                    <el-form-item label="所属设备" prop="eleName">
                        <el-input
                        v-model="queryParams.eleName"
                        placeholder="请输入所属设备"
                        clearable
                        @keyup.enter="handleQuery"
                        />
                    </el-form-item>
                    <el-form-item label="电池型号" prop="eleType">
                        <el-input
                        v-model="queryParams.eleType"
                        placeholder="请输入电池型号"
                        clearable
                        @keyup.enter="handleQuery"
                        />
                    </el-form-item>
                    <el-form-item label="组别" prop="eleGroup">
                        <el-input
                        v-model="queryParams.eleGroup"
                        placeholder="请输入组别"
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
                        v-hasPermi="['todo:todo_ele:add']"
                        >新增</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button
                        type="success"
                        plain
                        icon="Edit"
                        :disabled="single"
                        @click="handleUpdate"
                        v-hasPermi="['todo:todo_ele:edit']"
                        >修改</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button
                        type="danger"
                        plain
                        icon="Delete"
                        :disabled="multiple"
                        @click="handleDelete"
                        v-hasPermi="['todo:todo_ele:remove']"
                        >删除</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button
                        type="warning"
                        plain
                        icon="Download"
                        @click="handleExport"
                        v-hasPermi="['todo:todo_ele:export']"
                        >导出</el-button>
                </el-col>
                <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
                </el-row>

                <el-table v-loading="loading" :data="todo_eleList" @selection-change="handleSelectionChange"
                    :cell-style="cellStyle"
                    >
                    <el-table-column type="selection" width="55" align="center" />
                    <el-table-column label="序号" align="center" prop="eleNum" />
                    <el-table-column label="所属设备" align="center" prop="eleName" />
                    <el-table-column label="电池型号" align="center" prop="eleType" />
                    <el-table-column label="组别" align="center" prop="eleGroup" />
                    <el-table-column label="单体电池号" align="center" prop="eleGronum" />
                    <el-table-column label="内阻" align="center" prop="eleResis"/>
                    <el-table-column label="电压" align="center" prop="eleVoltage" />
                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                        <template #default="scope">
                        <el-button link type="primary" icon="Edit" @click="chartUpdate(scope.row)" v-hasPermi="['todo:todo_ele:edit']">历史数据</el-button>
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

                <!-- 添加或修改电池测试对话框 -->
                <el-dialog :title="title" v-model="open" width="500px" append-to-body>
                <el-form ref="todo_eleRef" :model="form" :rules="rules" label-width="80px">
                    <el-form-item label="序号" prop="eleNum">
                    <el-input v-model="form.eleNum" placeholder="请输入序号" />
                    </el-form-item>
                    <el-form-item label="所属设备" prop="eleName">
                    <el-input v-model="form.eleName" placeholder="请输入所属设备" />
                    </el-form-item>
                    <el-form-item label="电池型号" prop="eleType">
                    <el-input v-model="form.eleType" placeholder="请输入电池型号" />
                    </el-form-item>
                    <el-form-item label="组别" prop="eleGroup">
                    <el-input v-model="form.eleGroup" placeholder="请输入组别" />
                    </el-form-item>
                    <el-form-item label="单体电池号" prop="eleGronum">
                    <el-input v-model="form.eleGronum" placeholder="请输入单体电池号" />
                    </el-form-item>
                    <el-form-item label="内阻" prop="eleResis">
                    <el-input v-model="form.eleResis" placeholder="请输入内阻" />
                    </el-form-item>
                    <el-form-item label="电压" prop="eleVoltage">
                    <el-input v-model="form.eleVoltage" placeholder="请输入电压" />
                    </el-form-item>
                    <el-form-item label="内阻2024春" prop="eleResis1">
                    <el-input v-model="form.eleResis1" placeholder="请输入内阻2024春" />
                    </el-form-item>
                    <el-form-item label="电压2024春" prop="eleVoltage1">
                    <el-input v-model="form.eleVoltage1" placeholder="请输入电压2024春" />
                    </el-form-item>
                    <el-form-item label="内阻2023秋" prop="eleResis2">
                    <el-input v-model="form.eleResis2" placeholder="请输入内阻2023秋" />
                    </el-form-item>
                    <el-form-item label="电压2023秋" prop="eleVoltage2">
                    <el-input v-model="form.eleVoltage2" placeholder="请输入电压2023秋" />
                    </el-form-item>
                    <el-form-item label="内阻2023春" prop="eleResis3">
                    <el-input v-model="form.eleResis3" placeholder="请输入内阻2023春" />
                    </el-form-item>
                    <el-form-item label="电压2023春" prop="eleVoltage3">
                    <el-input v-model="form.eleVoltage3" placeholder="请输入电压2023春" />
                    </el-form-item>
                    <el-form-item label="内阻2022秋" prop="eleResis4">
                    <el-input v-model="form.eleResis4" placeholder="请输入内阻2022秋" />
                    </el-form-item>
                    <el-form-item label="电压2022秋" prop="eleVoltage4">
                    <el-input v-model="form.eleVoltage4" placeholder="请输入电压2022秋" />
                    </el-form-item>
                    <el-form-item label="内阻2022春" prop="eleResis5">
                    <el-input v-model="form.eleResis5" placeholder="请输入内阻2022春" />
                    </el-form-item>
                    <el-form-item label="电压2022春" prop="eleVoltage5">
                    <el-input v-model="form.eleVoltage5" placeholder="请输入电压2022春" />
                    </el-form-item>
                    <el-form-item label="内阻2021秋" prop="eleResis6">
                    <el-input v-model="form.eleResis6" placeholder="请输入内阻2021秋" />
                    </el-form-item>
                    <el-form-item label="电压2021秋" prop="eleVoltage6">
                    <el-input v-model="form.eleVoltage6" placeholder="请输入电压2021秋" />
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
<script setup name="Todo_ele">
    import { listTodo_ele, getTodo_ele, delTodo_ele, addTodo_ele, updateTodo_ele } from "@/api/todo/todo_ele";
    const { proxy } = getCurrentInstance();
    const todo_eleList = ref([]);
    const open = ref(false);
    const loading = ref(true);
    const showSearch = ref(true);
    const ids = ref([]);
    const single = ref(true);
    const multiple = ref(true);
    const total = ref(0);
    const title = ref("");
    const eleResis_Max = 4.9;

    const data = reactive({
    form: {},
    queryParams: {
        pageNum: 1,
        pageSize: 10,
        eleName: null,
        eleType: null,
        eleGroup: null,
    },
    rules: {
    }
    });

    const { queryParams, form, rules } = toRefs(data);

    /** 查询电池测试列表 */
    function getList() {
        loading.value = true;
        listTodo_ele(queryParams.value).then(response => {
            todo_eleList.value = response.rows;
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
            eleNum: null,
            eleName: null,
            eleType: null,
            eleGroup: null,
            eleGronum: null,
            eleResis: null,
            eleVoltage: null,
            eleResis1: null,
            eleVoltage1: null,
            eleResis2: null,
            eleVoltage2: null,
            eleResis3: null,
            eleVoltage3: null,
            eleResis4: null,
            eleVoltage4: null,
            eleResis5: null,
            eleVoltage5: null,
            eleResis6: null,
            eleVoltage6: null
        };
        proxy.resetForm("todo_eleRef");
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
        ids.value = selection.map(item => item.eleNum);
        single.value = selection.length != 1;
        multiple.value = !selection.length;
        }

        /** 新增按钮操作 */
        function handleAdd() {
        reset();
        open.value = true;
        title.value = "添加电池测试";
        }

        /** 修改按钮操作 */
        function handleUpdate(row) {
        reset();
        const _eleNum = row.eleNum || ids.value
        getTodo_ele(_eleNum).then(response => {
            form.value = response.data;
            open.value = true;
            title.value = "修改电池测试";
        });
        }

        /** 提交按钮 */
        function submitForm() {
        proxy.$refs["todo_eleRef"].validate(valid => {
            if (valid) {
            if (form.value.eleNum != null) {
                updateTodo_ele(form.value).then(response => {
                proxy.$modal.msgSuccess("修改成功");
                open.value = false;
                getList();
                });
            } else {
                addTodo_ele(form.value).then(response => {
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
        const _eleNums = row.eleNum || ids.value;
        proxy.$modal.confirm('是否确认删除电池测试编号为"' + _eleNums + '"的数据项？').then(function() {
            return delTodo_ele(_eleNums);
        }).then(() => {
            getList();
            proxy.$modal.msgSuccess("删除成功");
        }).catch(() => {});
        }

        /** 导出按钮操作 */
        function handleExport() {
        proxy.download('todo/todo_ele/export', {
            ...queryParams.value
        }, `todo_ele_${new Date().getTime()}.xlsx`)
        }

    getList();

</script>
<script>
import * as echarts from 'echarts'
import health_data from '@/api/todo/todo_health_data.json';
export default {
    data(){
        return{
            lin2data:null,
            line2option: null,
            lin3data:null,
            line3option: null,
        }
        
    },
    methods:{
        chartUpdate(row) {

            this.lin2data=[row.eleResis6,row.eleResis5,row.eleResis4,row.eleResis3,row.eleResis2,row.eleResis1,row.eleResis],
            this.lin3data=[row.eleVoltage6,row.eleVoltage5,row.eleVoltage4,row.eleVoltage3,row.eleVoltage2,row.eleVoltage1,row.eleVoltage],

            this.line2option.series[0].data=this.lin2data
            this.lineChart2.setOption(this.line2option,true)
            this.line3option.series[0].data=this.lin3data
            this.lineChart3.setOption(this.line3option,true)
        },
        cellStyle({row,column,rowIndes,columnIndex}){
            if(row.eleResis>=4.9&&columnIndex==6){    //eleResis_Max
                return {'color':'#FF0000'}
            }
            if(row.eleVoltage<=12.5&&columnIndex==7){
                return {'color':'#FF0000'}
            }else if(row.eleVoltage<=13&&columnIndex==7){
                return {'color':'#FF7B00'}
            }
        }
    },
    mounted() {
        this.myChart = echarts.init(this.$refs.myChart)
        this.myChart.setOption({
            title: {
                text: '16',
                subtext: '电池数量',
                left: 'center',
                top: '43%',
                subtextStyle: {
                  fontSize: 18
                }
            },
            tooltip: {
                trigger: 'item'
            },
            legend: {
                icon: 'circle',
                top: '0',
                left: 'right'
            },
            series: [
                {
                    name: '统计数量',
                    type: 'pie',
                    radius: ['40%', '55%'],
                    label: {
                        show: true,
                        padding: [0, -60],
                        overflow: 'none',
                        fontSize: '15',
                        fontWeight: 'bold',
                        formatter: '{d}%\n\n{c}'
                    },
                    labelLine: {
                        show: true,
                        length: 15,
                        length2: 60
                    },
                    itemStyle: {
                        normal: {
                            color: function (params) {
                                var colorList = ['#00C853', '#4FC3F7', '#F57F17']
                                return colorList[params.dataIndex]
                            }
                        }
                    },
                    data: [
                        { name: '健康', value: 14 },
                        { name: '一般', value: 2 },
                        { name: '不佳', value: 0 }
                    ]
                }
            ]
        })


        
        this.lineChart = echarts.init(this.$refs.lineChart)
        // var countries = ['Australia', 'Canada', 'China', 'Cuba', 'Finland', 'France',
        // 'Germany', 'Iceland', 'India', 'Japan', 'North Korea', 'South Korea', 'New Zealand',
        // 'Norway', 'Poland', 'Russia', 'Turkey', 'United Kingdom', 'United States'];
        const countries = [
            "艾默生Ⅰ-1",
            "艾默生Ⅰ-2",
            "艾默生Ⅰ-3",
            "艾默生Ⅰ-4",
            "艾默生Ⅱ-1",
            "艾默生Ⅱ-2",
            "艾默生Ⅱ-3",
            "艾默生Ⅱ-4",
            "动力源Ⅰ-1",
            "动力源Ⅰ-2",
            "动力源Ⅰ-3",
            "动力源Ⅰ-4",
            "动力源Ⅱ-1",
            "动力源Ⅱ-2",
            "动力源Ⅱ-3",
            "动力源Ⅱ-4",
        ];
        const datasetWithFilters = [];
        const seriesList = [];
        
        echarts.util.each(countries, function (country) {
            var datasetId = 'dataset_' + country;
            datasetWithFilters.push({
            id: datasetId,
            fromDatasetId: 'dataset_raw',
            transform: {
                type: 'filter',
                config: {
                and: [
                    { dimension: 'Year', gte: 1950 },
                    { dimension: 'Country', '=': country }
                ]
                }
            }
            });
            seriesList.push({
            type: 'line',
            datasetId: datasetId,
            showSymbol: false,
            name: country,
            endLabel: {
                show: true,
                formatter: function (params) {
                return params.value[3] + ': ' + params.value[0];
                }
            },
            labelLayout: {
                moveOverlap: 'shiftY'
            },
            emphasis: {
                focus: 'series'
            },
            encode: {
                x: 'Year',
                y: 'Income',
                label: ['Country', 'Income'],
                itemName: 'Year',
                tooltip: ['Income']
            }
            });
        });
        this.lineChart.setOption({
            animationDuration: 10000,
            dataset: [
            {
                id: 'dataset_raw',
                source: health_data
            },
            ...datasetWithFilters
            ],
            title: {
            text: '电池电阻总对比'
            },
            tooltip: {
            order: 'valueDesc',
            trigger: 'axis'
            },
            xAxis: {
            type: 'category',
            nameLocation: 'middle'
            },
            yAxis: {
            name: 'Income'
            },
            grid: {
            right: 140
            },
            series: seriesList
        });
        
        
        this.lineChart2 = echarts.init(this.$refs.lineChart2)
        this.line2option={
            title: {
            text: '内阻数据'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {},
            toolbox: {
                show: true,
                feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: { readOnly: false },
                magicType: { type: ['line', 'bar'] },
                restore: {},
                saveAsImage: {}
                }
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: ['2021.10','2022.3','2022.10', '2023.3', '2023.10', '2024.3', '2024.10', ]
            },
            yAxis: {
                type: 'value',
                axisLabel: {
                formatter: '{value} mΩ'
                }
            },
            series: [
                {
                name: '内阻',
                type: 'line',
                data: [4.6,4.5,4.52,4.61,4.65,4.65,4.67],
                markPoint: {
                    data: [
                    { type: 'max', name: 'Max' },
                    { type: 'min', name: 'Min' }
                    ]
                },
                //平均值线
                markLine: {
                    symbol:['none','none'],
                    //data: [{ type: 'average', name: 'Avg' }]
                    data:[{
                        name:'阈值',
                        yAxis:4.9 //eleResis_Max
                    }],
                    lineStyle:{
                        type:'dashed',
                        color:'#FF1D00',
                        width:1
                    }
                }
                },
            ]
        },
        this.lineChart2.setOption(this.line2option)

        this.lineChart3 = echarts.init(this.$refs.lineChart3)
        this.line3option={
            title: {
            text: '电压数据'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {},
            toolbox: {
                show: true,
                feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: { readOnly: false },
                magicType: { type: ['line', 'bar'] },
                restore: {},
                saveAsImage: {}
                }
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: ['2021.10','2022.3','2022.10', '2023.3', '2023.10', '2024.3', '2024.10', ]
            },
            yAxis: {
                type: 'value',
                axisLabel: {
                formatter: '{value} V'
                }
            },
            series: [
                {
                name: '电压',
                type: 'line',
                data: [13.2,13.1,13.11,13.15,13.20,13.22,13.05],
                markPoint: {
                    data: [
                    { type: 'max', name: 'Max' },
                    { type: 'min', name: 'Min' }
                    ]
                },
                //平均值线
                markLine: {
                    symbol:['none','none'],
                    //data: [{ type: 'average', name: 'Avg' }]
                    data:[
                        {
                        name:'上阈值',
                        yAxis:14.9
                        },
                        {
                        name:'下阈值',
                        yAxis:12.5 
                        }
                        ],
                    lineStyle:{
                        type:'dashed',
                        color:'#FF1D00',
                        width:1
                    }
                }
                },
            ]
        },
        this.lineChart3.setOption(this.line3option)
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
        "no4 no4 no5 no5"
        "no3 no3 no3 no3"
    }

    .no1 { grid-area: no1; }

    .no2 { grid-area: no2; }

    .no3 { grid-area: no3; }

    .no4 { grid-area: no4; }
    
    .no5 { grid-area: no5; }
</style>