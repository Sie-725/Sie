<template>
    <div class="container">
        <div class="no1" ref="lineChart" style="width: 800px; height: 400px; background-color: #ffffff; padding: 20px; border-radius: 20px;">
        </div>
        <div class="no2" ref="myChart" style="width: 400px; height: 400px; background-color: #ffffff; padding: 20px; border-radius: 20px;">
        </div>
        <div class="no3">
            <div class="app-container">
                <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
                <el-form-item label="时间" prop="date">
                    <el-input
                    v-model="queryParams.date"
                    placeholder="请输入时间"
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
                    v-hasPermi="['todo:todo_health:add']"
                    >新增</el-button>
                </el-col>
                <el-col :span="1.5">
                    <el-button
                    type="success"
                    plain
                    icon="Edit"
                    :disabled="single"
                    @click="handleUpdate"
                    v-hasPermi="['todo:todo_health:edit']"
                    >修改</el-button>
                </el-col>
                <el-col :span="1.5">
                    <el-button
                    type="danger"
                    plain
                    icon="Delete"
                    :disabled="multiple"
                    @click="handleDelete"
                    v-hasPermi="['todo:todo_health:remove']"
                    >删除</el-button>
                </el-col>
                <el-col :span="1.5">
                    <el-button
                    type="warning"
                    plain
                    icon="Download"
                    @click="handleExport"
                    v-hasPermi="['todo:todo_health:export']"
                    >导出</el-button>
                </el-col>
                <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
                </el-row>

                <el-table v-loading="loading" :data="todo_healthList" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center" />
                <el-table-column label="时间" align="center" prop="date" />
                <el-table-column label="温湿度" align="center" prop="temperature" />
                <el-table-column label="硬件" align="center" prop="hardware" />
                <el-table-column label="台账" align="center" prop="ledger" />
                <el-table-column label="告警" align="center" prop="alarm" />
                <el-table-column label="故障" align="center" prop="malfunction" />
                <el-table-column label="指标" align="center" prop="indicators" />
                <el-table-column label="年限" align="center" prop="life" />
                <el-table-column label="权重" align="center" prop="weight" />
                <el-table-column label="总分" align="center" prop="total" />
                <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                    <template #default="scope">
                    <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['todo:todo_health:edit']">修改</el-button>
                    <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['todo:todo_health:remove']">删除</el-button>
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

                <!-- 添加或修改机房健康对话框 -->
                <el-dialog :title="title" v-model="open" width="500px" append-to-body>
                <el-form ref="todo_healthRef" :model="form" :rules="rules" label-width="80px">
                    <el-form-item label="时间" prop="date">
                    <el-input v-model="form.date" placeholder="请输入时间" />
                    </el-form-item>
                    <el-form-item label="温湿度" prop="temperature">
                    <el-input v-model="form.temperature" placeholder="请输入温湿度" />
                    </el-form-item>
                    <el-form-item label="硬件" prop="hardware">
                    <el-input v-model="form.hardware" placeholder="请输入硬件" />
                    </el-form-item>
                    <el-form-item label="台账" prop="ledger">
                    <el-input v-model="form.ledger" placeholder="请输入台账" />
                    </el-form-item>
                    <el-form-item label="告警" prop="alarm">
                    <el-input v-model="form.alarm" placeholder="请输入告警" />
                    </el-form-item>
                    <el-form-item label="故障" prop="malfunction">
                    <el-input v-model="form.malfunction" placeholder="请输入故障" />
                    </el-form-item>
                    <el-form-item label="指标" prop="indicators">
                    <el-input v-model="form.indicators" placeholder="请输入指标" />
                    </el-form-item>
                    <el-form-item label="年限" prop="life">
                    <el-input v-model="form.life" placeholder="请输入年限" />
                    </el-form-item>
                    <el-form-item label="权重" prop="weight">
                    <el-input v-model="form.weight" placeholder="请输入权重" />
                    </el-form-item>
                    <el-form-item label="总分" prop="total">
                    <el-input v-model="form.total" placeholder="请输入总分" />
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
 <script setup  name="Todo_health">
    import { listTodo_health, getTodo_health, delTodo_health, addTodo_health, updateTodo_health } from "@/api/todo/todo_health";
    const { proxy } = getCurrentInstance();
    const todo_healthList = ref([]);
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
            date: null,
        },
        rules: {
        }
    });

    const { queryParams, form, rules } = toRefs(data);

    /** 查询机房健康列表 */
    function getList() {
        loading.value = true;
        listTodo_health(queryParams.value).then(response => {
            todo_healthList.value = response.rows;
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
            date: null,
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
        proxy.resetForm("todo_healthRef");
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
        ids.value = selection.map(item => item.date);
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
        const _date = row.date || ids.value
        getTodo_health(_date).then(response => {
            form.value = response.data;
            open.value = true;
            title.value = "修改机房健康";
        });
    }

    /** 提交按钮 */
    function submitForm() {
        proxy.$refs["todo_healthRef"].validate(valid => {
            if (valid) {
            if (form.value.date != null) {
                updateTodo_health(form.value).then(response => {
                proxy.$modal.msgSuccess("修改成功");
                open.value = false;
                getList();
                });
            } else {
                addTodo_health(form.value).then(response => {
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
        const _dates = row.date || ids.value;
        proxy.$modal.confirm('是否确认删除机房健康编号为"' + _dates + '"的数据项？').then(function() {
            return delTodo_health(_dates);
        }).then(() => {
            getList();
            proxy.$modal.msgSuccess("删除成功");
        }).catch(() => {});
    }

    /** 导出按钮操作 */
    function handleExport() {
        proxy.download('todo/todo_health/export', {
            ...queryParams.value
        }, `todo_health_${new Date().getTime()}.xlsx`)
    }

    getList();

</script>
<script>
import * as echarts from 'echarts'
import Todo_health from '../todo_health/index.vue'
export default {
    components: {
        Todo_health
    },
    data(){
        return{
            lin2data:null,
            line2option: null,
        }
        
    },
    methods:{
/*         chartUpdate(row) {
            this.lin2data=[row.optDec5,row.optDec4,row.optDec3,row.optDec2,row.optDec1,row.optDec],
            this.line2option.series[0].data=this.lin2data
            this.lineChart2.setOption(this.line2option,true)
        }, */
    },
    mounted() {
        this.myChart = echarts.init(this.$refs.myChart)
        this.myChart.setOption({
            title: {
                text: '32469',
                subtext: '机房数',
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
                        { name: '健康', value: 1048 },
                        { name: '一般', value: 735 },
                        { name: '不佳', value: 180 }
                    ]
                }
            ]
        })


        this.lineChart = echarts.init(this.$refs.lineChart)
        this.lineChart.setOption({
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
        "no4 no4 no5 no5"
        "no3 no3 no3 no3"
    }

    .no1 { grid-area: no1; }

    .no2 { grid-area: no2; }

    .no3 { grid-area: no3; }

    .no4 { grid-area: no4; }

    .no5 { grid-area: no5; }
</style>