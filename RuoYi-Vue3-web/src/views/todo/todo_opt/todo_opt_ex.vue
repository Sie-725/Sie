<template>
    <div class="container">
        <!-- <div class="no1" ref="lineChart" style="width: 800px; height: 400px; background-color: #ffffff; padding: 20px; border-radius: 20px;">
        </div> -->
        <div class="no2" ref="myChart" style="width: 400px; height: 400px; background-color: #ffffff; padding: 20px; border-radius: 20px;">
        </div>
        <div class="no4" ref="lineChart2" style="width: 800px; height: 400px; background-color: #ffffff; padding: 20px; border-radius: 20px;">
        </div>
<!--         <div class="no5" ref="lineChart3" style="width: 700px; height: 400px; background-color: #ffffff; padding: 20px; border-radius: 20px;">
        </div> -->
        <div class="no3">
            <div class="app-container">
                <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
                    <el-form-item label="光缆名称" prop="optName">
                        <el-input
                        v-model="queryParams.optName"
                        placeholder="请输入光缆名称"
                        clearable
                        @keyup.enter="handleQuery"
                        />
                    </el-form-item>
                    <el-form-item label="光缆区间" prop="optStage">
                        <el-input
                        v-model="queryParams.optStage"
                        placeholder="请输入光缆区间"
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
                        v-hasPermi="['todo:todo_opt:add']"
                        >新增</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button
                        type="success"
                        plain
                        icon="Edit"
                        :disabled="single"
                        @click="handleUpdate"
                        v-hasPermi="['todo:todo_opt:edit']"
                        >修改</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button
                        type="danger"
                        plain
                        icon="Delete"
                        :disabled="multiple"
                        @click="handleDelete"
                        v-hasPermi="['todo:todo_opt:remove']"
                        >删除</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button
                        type="warning"
                        plain
                        icon="Download"
                        @click="handleExport"
                        v-hasPermi="['todo:todo_opt:export']"
                        >导出</el-button>
                    </el-col>
                    <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
                </el-row>

                <el-table v-loading="loading" :data="todo_optList" @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="55" align="center" />
                    <el-table-column label="光缆名称" align="center" prop="optName" />
                    <el-table-column label="光纤序号" align="center" prop="optNum" />
                    <el-table-column label="测试区段" align="center" prop="optStage" />
                    <el-table-column label="光纤长度" align="center" prop="optLength" />
                    <el-table-column label="光纤衰耗" align="center" prop="optDec" />
                    <el-table-column label="备注" align="center" prop="optRemark" />
                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                        <template #default="scope">
                        <el-button link type="primary" icon="Edit" @click="chartUpdate(scope.row)" v-hasPermi="['todo:todo_opt:edit']">历史数据</el-button>
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
                
                <!-- 添加或修改光缆测试对话框 -->
                <el-dialog :title="title" v-model="open" width="500px" append-to-body>
                <el-form ref="todo_optRef" :model="form" :rules="rules" label-width="80px">
                    <el-form-item label="光缆名称" prop="optName">
                    <el-input v-model="form.optName" type="textarea" placeholder="请输入内容" />
                    </el-form-item>
                    <el-form-item label="光缆序号" prop="optNum">
                    <el-input v-model="form.optNum" type="textarea" placeholder="请输入内容" />
                    </el-form-item>
                    <el-form-item label="光缆区间" prop="optStage">
                    <el-input v-model="form.optStage" type="textarea" placeholder="请输入内容" />
                    </el-form-item>
                    <el-form-item label="光缆距离" prop="optLength">
                    <el-input v-model="form.optLength" type="textarea" placeholder="请输入内容" />
                    </el-form-item>
                    <el-form-item label="光缆衰耗" prop="optDec">
                    <el-input v-model="form.optDec" type="textarea" placeholder="请输入内容" />
                    </el-form-item>
                    <el-form-item label="备注信息" prop="optRemark">
                    <el-input v-model="form.optRemark" type="textarea" placeholder="请输入内容" />
                    </el-form-item>
                    <el-form-item label="" prop="optDec1">
                    <el-input v-model="form.optDec1" type="textarea" placeholder="请输入内容" />
                    </el-form-item>
                    <el-form-item label="${comment}" prop="optDec2">
                    <el-input v-model="form.optDec2" type="textarea" placeholder="请输入内容" />
                    </el-form-item>
                    <el-form-item label="${comment}" prop="optDec3">
                    <el-input v-model="form.optDec3" type="textarea" placeholder="请输入内容" />
                    </el-form-item>
                    <el-form-item label="${comment}" prop="optDec4">
                    <el-input v-model="form.optDec4" type="textarea" placeholder="请输入内容" />
                    </el-form-item>
                    <el-form-item label="${comment}" prop="optDec5">
                    <el-input v-model="form.optDec5" type="textarea" placeholder="请输入内容" />
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
 <script setup  name="Todo_opt">
    import { listTodo_opt, getTodo_opt, delTodo_opt, addTodo_opt, updateTodo_opt } from "@/api/todo/todo_opt";
    const { proxy } = getCurrentInstance();
    const todo_optList = ref([]);
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
        optName: null,
        optNum: null,
        optStage: null,
        optLength: null,
        optDec: null,
        optRemark: null
    },
    rules: {
    }
    });

    const { queryParams, form, rules } = toRefs(data);

    /** 查询光缆测试列表 */
    function getList() {
        loading.value = true;
        listTodo_opt(queryParams.value).then(response => {
            todo_optList.value = response.rows;
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
            optName: null,
            optNum: null,
            optStage: null,
            optLength: null,
            optDec: null,
            optRemark: null,
            optDec1: null,
            optDec2: null,
            optDec3: null,
            optDec4: null,
            optDec5: null
        };
        proxy.resetForm("todo_optRef");
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
        ids.value = selection.map(item => item.optName);
        single.value = selection.length != 1;
        multiple.value = !selection.length;
    }

    /** 新增按钮操作 */
    function handleAdd() {
        reset();
        open.value = true;
        title.value = "添加光缆测试";
    }

    /** 修改按钮操作 */
    function handleUpdate(row) {
        reset();
        const _optName = row.optName || ids.value
        getTodo_opt(_optName).then(response => {
            form.value = response.data;
            open.value = true;
            title.value = "修改光缆测试";
        });
    }

    /** 提交按钮 */
    function submitForm() {
        proxy.$refs["todo_optRef"].validate(valid => {
            if (valid) {
            if (form.value.optName != null) {
                updateTodo_opt(form.value).then(response => {
                proxy.$modal.msgSuccess("修改成功");
                open.value = false;
                getList();
                });
            } else {
                addTodo_opt(form.value).then(response => {
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
        const _optNames = row.optName || ids.value;
        proxy.$modal.confirm('是否确认删除光缆测试编号为"' + _optNames + '"的数据项？').then(function() {
            return delTodo_opt(_optNames);
        }).then(() => {
            getList();
            proxy.$modal.msgSuccess("删除成功");
        }).catch(() => {});
        }

        /** 导出按钮操作 */
        function handleExport() {
        proxy.download('todo/todo_opt/export', {
            ...queryParams.value
        }, `todo_opt_${new Date().getTime()}.xlsx`)
    }

    getList();

</script>
<script>
import * as echarts from 'echarts'
import Todo_ele from '../todo_ele/index.vue'
export default {
    components: {
        Todo_ele
    },
    data(){
        return{
            lin2data:null,
            line2option: null,
        }
        
    },
    methods:{
        chartUpdate(row) {
            this.lin2data=[row.optDec5,row.optDec4,row.optDec3,row.optDec2,row.optDec1,row.optDec],
            this.line2option.series[0].data=this.lin2data
            this.lineChart2.setOption(this.line2option,true)
        },
    },
    mounted() {
        this.myChart = echarts.init(this.$refs.myChart)
        this.myChart.setOption({
            title: {
                text: '259',
                subtext: '电路数',
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
                        { name: '健康', value: 236 },
                        { name: '一般', value: 12 },
                        { name: '不佳', value: 11 }
                    ]
                }
            ]
        })



/*         this.lineChart = echarts.init(this.$refs.lineChart)
        this.lineChart.setOption({
            title: {
                text: '机房健康度分析'
            },
            // 提示框
            tooltip: {
                trigger: 'axis'
            },
            // 图例
            legend: {
                icon: 'circle',
                left: 'center',
                top: 0,
                data: ['健康', '一般', '不佳']
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
                data: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name: '健康',
                    type: 'line',
                    // smooth: true, // 平滑曲线显示
                    data: [120, 132, 101, 134, 190, 230, 210, 201, 234, 290, 230, 210]
                },
                {
                    name: '一般',
                    type: 'line',
                    // smooth: true,
                    data: [100, 82, 91, 54, 90, 76, 110, 81, 104, 90, 130, 110]
                },
                {
                    name: '不佳',
                    type: 'line',
                    stack: '总量',
                    // smooth: true,
                    data: [10, 22, 21, 14, 19, 13, 20, 11, 34, 29, 20, 10]
                }
            ]
        }) */
        
        this.lineChart2 = echarts.init(this.$refs.lineChart2)
        this.line2option={
            title: {
            text: '光功率趋势'
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
                data: ['2022.3','2022.10', '2023.3', '2023.10', '2024.3', '2024.10', ]
            },
            yAxis: {
                type: 'value',
                axisLabel: {
                formatter: '{value} dBm'
                }
            },
            series: [
                {
                name: 'Highest',
                type: 'line',
                data: [1,1,2,3,1,3,1],
                markPoint: {
                    data: [
                    { type: 'max', name: 'Max' },
                    { type: 'min', name: 'Min' }
                    ]
                },
                markLine: {
                    data: [{ type: 'average', name: 'Avg' }]
                }
                },
            ]
        },
        this.lineChart2.setOption(this.line2option)
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
        "no4 no4 no2 no2"
        /* "no4 no4 no5 no5" */
        "no3 no3 no3 no3"
    }

    /* .no1 { grid-area: no1; } */

    .no2 { grid-area: no2; }

    .no3 { grid-area: no3; }

    .no4 { grid-area: no4; }

    .no5 { grid-area: no5; }
</style>