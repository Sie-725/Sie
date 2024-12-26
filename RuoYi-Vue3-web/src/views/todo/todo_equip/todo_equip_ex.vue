<template>
    <div class="container">
        <div class="no1" ref="lineChart" style="width: 800px; height: 400px; background-color: #ffffff; padding: 20px; border-radius: 20px;">
        </div>
        <div class="no2" ref="myChart" style="width: 800px; height: 400px; background-color: #ffffff; padding: 20px; border-radius: 20px;">
        </div>
        <div class="no3">
            <div class="app-container">
                <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
                    <el-form-item label="系统" prop="system">
                        <el-input
                        v-model="queryParams.system"
                        placeholder="请输入系统"
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
                    <el-form-item label="设备" prop="name">
                        <el-input
                        v-model="queryParams.name"
                        placeholder="请输入设备"
                        clearable
                        @keyup.enter="handleQuery"
                        />
                    </el-form-item>
                    <el-form-item label="运行时间" prop="life">
                        <el-input
                        v-model="queryParams.life"
                        placeholder="请输入运行时间"
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
                    <el-form-item label="类型" prop="type">
                        <el-input
                        v-model="queryParams.type"
                        placeholder="请输入类型"
                        clearable
                        @keyup.enter="handleQuery"
                        />
                    </el-form-item>
                    <el-form-item label="厂家" prop="factory">
                        <el-input
                        v-model="queryParams.factory"
                        placeholder="请输入厂家"
                        clearable
                        @keyup.enter="handleQuery"
                        />
                    </el-form-item>
                    <el-form-item label="型号" prop="model">
                        <el-input
                        v-model="queryParams.model"
                        placeholder="请输入型号"
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
                        v-hasPermi="['todo:todo_equip:add']"
                        >新增</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button
                        type="success"
                        plain
                        icon="Edit"
                        :disabled="single"
                        @click="handleUpdate"
                        v-hasPermi="['todo:todo_equip:edit']"
                        >修改</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button
                        type="danger"
                        plain
                        icon="Delete"
                        :disabled="multiple"
                        @click="handleDelete"
                        v-hasPermi="['todo:todo_equip:remove']"
                        >删除</el-button>
                    </el-col>
                    <el-col :span="1.5">
                        <el-button
                        type="warning"
                        plain
                        icon="Download"
                        @click="handleExport"
                        v-hasPermi="['todo:todo_equip:export']"
                        >导出</el-button>
                    </el-col>
                    <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
                </el-row>

                <el-table v-loading="loading" :data="todo_equipList" @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="55" align="center" />
                    <el-table-column label="序号" align="center" prop="id" />
                    <el-table-column label="系统" align="center" prop="system" />
                    <el-table-column label="线路" align="center" prop="line" />
                    <el-table-column label="设备" align="center" prop="name" />
                    <el-table-column label="运行时间" align="center" prop="life" />
                    <el-table-column label="地区" align="center" prop="area" />
                    <el-table-column label="类型" align="center" prop="type" />
                    <el-table-column label="厂家" align="center" prop="factory" />
                    <el-table-column label="型号" align="center" prop="model" />
                    <el-table-column label="功耗" align="center" prop="power" />
                    <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                        <template #default="scope">
                        <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['todo:todo_equip:edit']">修改</el-button>
                        <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['todo:todo_equip:remove']">删除</el-button>
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

                <!-- 添加或修改设备统计对话框 -->
                <el-dialog :title="title" v-model="open" width="500px" append-to-body>
                    <el-form ref="todo_equipRef" :model="form" :rules="rules" label-width="80px">
                        <el-form-item label="系统" prop="system">
                        <el-input v-model="form.system" placeholder="请输入系统" />
                        </el-form-item>
                        <el-form-item label="线路" prop="line">
                        <el-input v-model="form.line" placeholder="请输入线路" />
                        </el-form-item>
                        <el-form-item label="设备" prop="name">
                        <el-input v-model="form.name" placeholder="请输入设备" />
                        </el-form-item>
                        <el-form-item label="运行时间" prop="life">
                        <el-input v-model="form.life" placeholder="请输入运行时间" />
                        </el-form-item>
                        <el-form-item label="地区" prop="area">
                        <el-input v-model="form.area" placeholder="请输入地区" />
                        </el-form-item>
                        <el-form-item label="状态" prop="status">
                        <el-input v-model="form.status" placeholder="请输入状态" />
                        </el-form-item>
                        <el-form-item label="类型" prop="type">
                        <el-input v-model="form.type" placeholder="请输入类型" />
                        </el-form-item>
                        <el-form-item label="厂家" prop="factory">
                        <el-input v-model="form.factory" placeholder="请输入厂家" />
                        </el-form-item>
                        <el-form-item label="型号" prop="model">
                        <el-input v-model="form.model" placeholder="请输入型号" />
                        </el-form-item>
                        <el-form-item label="功耗" prop="power">
                        <el-input v-model="form.power" placeholder="请输入功耗" />
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
    import { listTodo_equip, getTodo_equip, delTodo_equip, addTodo_equip, updateTodo_equip } from "@/api/todo/todo_equip";

    const { proxy } = getCurrentInstance();

    const todo_equipList = ref([]);
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
        system: null,
        line: null,
        name: null,
        life: null,
        area: null,
        type: null,
        factory: null,
        model: null,
    },
    rules: {
        system: [
        { required: true, message: "系统不能为空", trigger: "blur" }
        ],
        line: [
        { required: true, message: "线路不能为空", trigger: "blur" }
        ],
        name: [
        { required: true, message: "设备不能为空", trigger: "blur" }
        ],
    }
    });

    const { queryParams, form, rules } = toRefs(data);

    /** 查询设备统计列表 */
    function getList() {
    loading.value = true;
    listTodo_equip(queryParams.value).then(response => {
        todo_equipList.value = response.rows;
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
        system: null,
        line: null,
        name: null,
        life: null,
        area: null,
        status: null,
        type: null,
        factory: null,
        model: null,
        power: null
    };
    proxy.resetForm("todo_equipRef");
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
    title.value = "添加设备统计";
    }

    /** 修改按钮操作 */
    function handleUpdate(row) {
    reset();
    const _id = row.id || ids.value
    getTodo_equip(_id).then(response => {
        form.value = response.data;
        open.value = true;
        title.value = "修改设备统计";
    });
    }

    /** 提交按钮 */
    function submitForm() {
    proxy.$refs["todo_equipRef"].validate(valid => {
        if (valid) {
        if (form.value.id != null) {
            updateTodo_equip(form.value).then(response => {
            proxy.$modal.msgSuccess("修改成功");
            open.value = false;
            getList();
            });
        } else {
            addTodo_equip(form.value).then(response => {
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
    proxy.$modal.confirm('是否确认删除设备统计编号为"' + _ids + '"的数据项？').then(function() {
        return delTodo_equip(_ids);
    }).then(() => {
        getList();
        proxy.$modal.msgSuccess("删除成功");
    }).catch(() => {});
    }

    /** 导出按钮操作 */
    function handleExport() {
    proxy.download('todo/todo_equip/export', {
        ...queryParams.value
    }, `todo_equip_${new Date().getTime()}.xlsx`)
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
                text: '设备型号统计',
            },
            tooltip: {
                trigger: 'item'
            },
            legend: {
                icon: 'circle',
                top: '0',
                left: 'right'
            },
            series: {
                type: 'sunburst',
                // emphasis: {
                //     focus: 'ancestor'
                // },
                data: [{
                    name: '数据网',
                    children: [
                    {
                        name: '中兴',
                        value: 18,
                        children: [
                        {
                            name: '6800-8E',
                            value: 16
                        },
                        {
                            name: 'M6000-2S10',
                            value: 2
                        }
                        ]
                    },
                    {
                        name: '华三',
                        value: 158,
                        children: [
                        {
                            name: 'SR6604',
                            value: 120
                        },
                        {
                            name: 'SR6608',
                            value: 36
                        },
                        {
                            name: 'SR8808',
                            value: 2
                        }
                        ]
                    },
                    {
                        name: '华为',
                        value: 753,
                        children: [
                        {
                            name: 'NE20E',
                            value: 586
                        },
                        {
                            name: 'NE40E',
                            value: 62
                        },
                        {
                            name: 'NE8000E',
                            value: 105
                        }]
                    }]
                },
                {
                    name: '传输网',
                    children: [
                    {
                        name: '华为',
                        children: [
                        {
                            name: 'OSN 7500',
                            value: 64
                        },
                        {
                            name: 'OSN 3500',
                            value: 20
                        }
                        ]
                    },
                    {
                        name: '中兴',
                        children: [
                        {
                            name: 'ZXMP S385',
                            value: 30
                        },
                        {
                            name: 'ZXMP S330',
                            value: 20
                        },
                        {
                            name: 'ZXMP S320',
                            value: 20
                        }
                        ]
                    }
                    ]
                }
                ],
                radius: [0, '90%'],
                label: {
                rotate: 'radial'
                }
            }
        })


        this.lineChart = echarts.init(this.$refs.lineChart)
        this.lineChart.setOption({
            title: {
                text: '设备时间统计'
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
                type: 'time', // 设置横坐标为时间类型
                axisLabel: {
                    formatter: function (value) {
                        var date = new Date(value);
                        var year = date.getFullYear();
                        var month = (date.getMonth() + 1).toString().padStart(2, '0');
                        var day = date.getDate().toString().padStart(2, '0');
                        return `${year}-${month}-${day}`; // 格式化日期显示为YYYY-MM-DD
                    }}
            },
            yAxis: {
            },
            series: [{
                symbolSize: 8,
                data: [
                    ['2012/5/1',6],
                    ['2014/5/1',4],
                    ['2022/8/1',14],
                    ['2016/3/1',8],
                    ['2016/10/1',4],
                    ['2016/12/1',161],
                    ['2016/4/1',4],
                    ['2017/6/1',9],
                    ['2018/10/1',7],
                    ['2018/11/1',14],
                    ['2018/12/1',5],
                    ['2021/8/1',19],
                    ['2023/6/1',12],
                    ['2024/6/1',15],
                    ['2017/2/1',63],
                    ['2017/3/1',70],
                    ['2021/10/1',2],
                    ['2023/8/1',14],
                    ['2024/3/1',17],
                    ['2023/7/1',5],
                    ['2018/8/1',16],
                    ['2018/7/1',11],
                    ['2022/1/1',2],
                    ['2019/11/1',6],
                    ['2017/1/1',99],
                    ['2016/7/1',17],
                    ['2019/12/1',14],
                    ['2024/5/1',1],
                    ['2019/1/1',4],
                    ['2022/5/1',4],
                    ['2022/4/1',2],
                    ['2017/12/1',27],
                    ['2011/9/1',9],
                    ['2023/5/1',4],
                    ['2017/8/1',30],
                    ['2017/5/1',12],
                    ['2021/7/1',2],
                    ['2017/7/1',18],
                    ['2017/4/1',81],
                    ['2022/7/1',3],
                    ['2018/6/1',12],
                    ['2021/9/1',2],
                    ['2014/12/1',28],
                    ['2014/10/1',5],
                    ['2019/8/1',7],
                    ['2014/9/1',4],
                    ['2016/5/1',6],
                    ['2018/9/1',2],
                    ['2024/10/1',2],
                    ['2022/11/1',4],
                    ['2022/12/1',6],
                    ['2020/7/1',6],
                    ['2022/9/1',19],
                    ['2016/11/1',3],
                    ['2018/1/1',9],
                    ['2020/9/1',1],
                    ['2019/3/1',1],
                    ['2020/4/1',1],
                    ['2019/9/1',21],
                    ['2017/11/1',2],
                    ['2018/5/1',2],
                    ['2022/6/1',2],
                    ['2016/8/1',8],
                    ['2024/6/21',11],
                    ['2019/10/8',1],
                    ['2015/6/1',1],
                    ['2023/11/1',1],
                    ['2002/5/1',2],
                    ['2002/6/1',2],
                    ['2012/6/1',2],
                    ['2005/7/1',1],
                    ['2004/6/1',1],
                    ['2002/5/10',1],
                    ['2018/5/5',1],
                    ['2006/10/1',1],
                    ['2007/6/1',2],
                    ['2007/6/9',1],
                    ['2011/7/1',1],
                    ['2011/10/1',3],
                    ['2009/9/1',1],
                    ['2010/5/1',2],
                    ['2008/9/1',1],
                    ['1999/9/1',2],
                    ['2024/6/18',3],
                    ['2020/12/1',1],
                ],
                type: 'scatter'
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