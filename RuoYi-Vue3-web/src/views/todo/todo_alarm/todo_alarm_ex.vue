<template>
    <div class="container">
        <div class="no1" ref="lineChart" style="width: 700px; height: 500px; background-color: #ffffff; padding: 20px; border-radius: 20px;">
        </div>
        <div class="no2" ref="myChart" style="width: 800px; height: 500px; background-color: #ffffff; padding: 20px; border-radius: 20px;">
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
                <el-form-item label="端口" prop="port">
                    <el-input
                    v-model="queryParams.port"
                    placeholder="请输入端口"
                    clearable
                    @keyup.enter="handleQuery"
                    />
                </el-form-item>
                <el-form-item label="等级" prop="lever">
                    <el-select v-model="queryParams.lever" placeholder="请选择等级" clearable>
                    <el-option
                        v-for="dict in todo_alarm_lever"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                    />
                    </el-select>
                </el-form-item>
                <el-form-item label="描述" prop="des">
                    <el-input
                    v-model="queryParams.des"
                    placeholder="请输入描述"
                    clearable
                    @keyup.enter="handleQuery"
                    />
                </el-form-item>
                <el-form-item label="日期" prop="date">
                    <el-input
                    v-model="queryParams.date"
                    placeholder="请输入日期"
                    clearable
                    @keyup.enter="handleQuery"
                    />
                </el-form-item>
                <el-form-item label="状态" prop="status">
                    <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
                    <el-option
                        v-for="dict in todo_alarm_status"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                    />
                    </el-select>
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
                    v-hasPermi="['todo:todo_alarm:add']"
                    >新增</el-button>
                </el-col>
                <el-col :span="1.5">
                    <el-button
                    type="success"
                    plain
                    icon="Edit"
                    :disabled="single"
                    @click="handleUpdate"
                    v-hasPermi="['todo:todo_alarm:edit']"
                    >修改</el-button>
                </el-col>
                <el-col :span="1.5">
                    <el-button
                    type="danger"
                    plain
                    icon="Delete"
                    :disabled="multiple"
                    @click="handleDelete"
                    v-hasPermi="['todo:todo_alarm:remove']"
                    >删除</el-button>
                </el-col>
                <el-col :span="1.5">
                    <el-button
                    type="warning"
                    plain
                    icon="Download"
                    @click="handleExport"
                    v-hasPermi="['todo:todo_alarm:export']"
                    >导出</el-button>
                </el-col>
                <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
                </el-row>

                <el-table v-loading="loading" :data="todo_alarmList" @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="55" align="center" />
                <el-table-column label="序号" align="center" prop="id" />
                <el-table-column label="系统" align="center" prop="system" />
                <el-table-column label="线路" align="center" prop="line" />
                <el-table-column label="设备" align="center" prop="name" />
                <el-table-column label="端口" align="center" prop="port" />
                <el-table-column label="等级" align="center" prop="lever">
                    <template #default="scope">
                    <dict-tag :options="todo_alarm_lever" :value="scope.row.lever"/>
                    </template>
                </el-table-column>
                <el-table-column label="描述" align="center" prop="des" />
                <el-table-column label="日期" align="center" prop="date" />
                <el-table-column label="状态" align="center" prop="status">
                    <template #default="scope">
                    <dict-tag :options="todo_alarm_status" :value="scope.row.status"/>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                    <template #default="scope">
                    <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['todo:todo_alarm:edit']">修改</el-button>
                    <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['todo:todo_alarm:remove']">删除</el-button>
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

                <!-- 添加或修改故障统计对话框 -->
                <el-dialog :title="title" v-model="open" width="500px" append-to-body>
                <el-form ref="todo_alarmRef" :model="form" :rules="rules" label-width="80px">
                    <el-form-item label="系统" prop="system">
                    <el-input v-model="form.system" placeholder="请输入系统" />
                    </el-form-item>
                    <el-form-item label="线路" prop="line">
                    <el-input v-model="form.line" placeholder="请输入线路" />
                    </el-form-item>
                    <el-form-item label="设备" prop="name">
                    <el-input v-model="form.name" placeholder="请输入设备" />
                    </el-form-item>
                    <el-form-item label="端口" prop="port">
                    <el-input v-model="form.port" placeholder="请输入端口" />
                    </el-form-item>
                    <el-form-item label="等级" prop="lever">
                    <el-radio-group v-model="form.lever">
                        <el-radio
                        v-for="dict in todo_alarm_lever"
                        :key="dict.value"
                        :label="dict.value"
                        >{{dict.label}}</el-radio>
                    </el-radio-group>
                    </el-form-item>
                    <el-form-item label="描述" prop="des">
                    <el-input v-model="form.des" placeholder="请输入描述" />
                    </el-form-item>
                    <el-form-item label="日期" prop="date">
                    <el-input v-model="form.date" placeholder="请输入日期" />
                    </el-form-item>
                    <el-form-item label="状态" prop="status">
                    <el-radio-group v-model="form.status">
                        <el-radio
                        v-for="dict in todo_alarm_status"
                        :key="dict.value"
                        :label="dict.value"
                        >{{dict.label}}</el-radio>
                    </el-radio-group>
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
    import { listTodo_alarm, getTodo_alarm, delTodo_alarm, addTodo_alarm, updateTodo_alarm } from "@/api/todo/todo_alarm";

    const { proxy } = getCurrentInstance();
    const { todo_alarm_status, todo_alarm_lever } = proxy.useDict('todo_alarm_status', 'todo_alarm_lever');

    const todo_alarmList = ref([]);
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
        port: null,
        lever: null,
        des: null,
        date: null,
        status: null
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
        lever: [
        { required: true, message: "等级不能为空", trigger: "change" }
        ],
        status: [
        { required: true, message: "状态不能为空", trigger: "change" }
        ]
    }
    });

    const { queryParams, form, rules } = toRefs(data);

    /** 查询故障统计列表 */
    function getList() {
    loading.value = true;
    listTodo_alarm(queryParams.value).then(response => {
        todo_alarmList.value = response.rows;
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
        port: null,
        lever: null,
        des: null,
        date: null,
        status: null
    };
    proxy.resetForm("todo_alarmRef");
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
    title.value = "添加故障统计";
    }

    /** 修改按钮操作 */
    function handleUpdate(row) {
    reset();
    const _id = row.id || ids.value
    getTodo_alarm(_id).then(response => {
        form.value = response.data;
        open.value = true;
        title.value = "修改故障统计";
    });
    }

    /** 提交按钮 */
    function submitForm() {
    proxy.$refs["todo_alarmRef"].validate(valid => {
        if (valid) {
        if (form.value.id != null) {
            updateTodo_alarm(form.value).then(response => {
            proxy.$modal.msgSuccess("修改成功");
            open.value = false;
            getList();
            });
        } else {
            addTodo_alarm(form.value).then(response => {
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
    proxy.$modal.confirm('是否确认删除故障统计编号为"' + _ids + '"的数据项？').then(function() {
        return delTodo_alarm(_ids);
    }).then(() => {
        getList();
        proxy.$modal.msgSuccess("删除成功");
    }).catch(() => {});
    }

    /** 导出按钮操作 */
    function handleExport() {
    proxy.download('todo/todo_alarm/export', {
        ...queryParams.value
    }, `todo_alarm_${new Date().getTime()}.xlsx`)
    }

    getList();
</script>
<script>
import * as echarts from 'echarts'
import health_data from '@/api/todo/todo_health_data.json';
export default {
    data(){
        return{

        }
        
    },
    methods:{
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
            tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b}: {c} ({d}%)'
        },
        legend: {
            data: [
            'TMIS','设备','通道', 'THDS','SCADA','PSIS','IVMS','VCS', 'Other'
            ]
        },
        series: [
            {
            name: 'Access From',
            type: 'pie',
            selectedMode: 'single',
            radius: [0, '30%'],
            label: {
                position: 'inner',
                fontSize: 14
            },
            labelLine: {
                show: false
            },
            data: [
                { value: 1548, name: '数通' },
                { value: 1560, name: '传输' },
                { value: 679, name: '无线', selected: true }
            ]
            },
            {
            name: '重要告警',
            type: 'pie',
            radius: ['45%', '60%'],
            labelLine: {
                length: 30
            },
            label: {
                formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
                backgroundColor: '#F6F8FC',
                borderColor: '#8C8D8E',
                borderWidth: 1,
                borderRadius: 4,
                rich: {
                a: {
                    color: '#6E7079',
                    lineHeight: 22,
                    align: 'center'
                },
                hr: {
                    borderColor: '#8C8D8E',
                    width: '100%',
                    borderWidth: 1,
                    height: 0
                },
                b: {
                    color: '#4C5058',
                    fontSize: 14,
                    fontWeight: 'bold',
                    lineHeight: 33
                },
                per: {
                    color: '#fff',
                    backgroundColor: '#4C5058',
                    padding: [3, 4],
                    borderRadius: 4
                }
                }
            },
            data: [
                { value: 104, name: 'TMIS' },
                { value: 335, name: '设备' },
                { value: 310, name: '通道' },
                { value: 251, name: 'THDS' },
                { value: 634, name: 'SCADA' },
                { value: 147, name: 'PSIS' },
                { value: 135, name: 'IVMS' },
                { value: 102, name: 'VCS' },
                { value: 102, name: 'Other' }
            ]
            }
        ]
        })


        
        this.lineChart = echarts.init(this.$refs.lineChart)
        this.lineChart.setOption({
            title: {
            text: '故障分布',
            subtext: 'Data from: Heinz 2003'
        },
        grid: {
            left: '3%',
            right: '7%',
            bottom: '7%',
            containLabel: true
        },
        tooltip: {
            // trigger: 'axis',
            showDelay: 0,
            formatter: function (params) {
            if (params.value.length > 1) {
                return (
                params.seriesName +
                ' :<br/>' +
                params.value[0] +
                ' ' +
                params.value[1] +
                ' '
                );
            } else {
                return (
                params.seriesName +
                ' :<br/>' +
                params.name +
                ' : ' +
                params.value +
                ' '
                );
            }
            },
            axisPointer: {
            show: true,
            type: 'cross',
            lineStyle: {
                type: 'dashed',
                width: 1
            }
            }
        },
        toolbox: {
            feature: {
            dataZoom: {},
            brush: {
                type: ['rect', 'polygon', 'clear']
            }
            }
        },
        brush: {},
        legend: {
            data: ['数据网', '传输网'],
            left: 'center',
            bottom: 10
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
        yAxis: [
            {
            type: 'value',
            scale: true,
            axisLabel: {
                formatter: '{value}'
            },
            splitLine: {
                show: false
            }
            }
        ],
        series: [
            {
            name: '数据网',
            type: 'scatter',
            emphasis: {
                focus: 'series'
            },
            // prettier-ignore
            data: [['2024/1/5',5],
['2024/1/6',6],
['2024/1/9',9],
['2024/1/10',10],
['2024/1/23',15],
['2024/1/24',14],
['2024/1/24',12],
['2024/1/24',12],
['2024/2/1',1],
['2024/2/11',11],
['2024/2/14',2],
['2024/2/16',16],
['2024/2/25',9],
['2024/3/8',8],
['2024/3/10',7],
['2024/3/13',8],
['2024/3/14',9],
['2024/3/17',5],
['2024/3/22',4],
['2024/3/24',3],
['2024/3/26',2],
['2024/4/1',1],
['2024/4/3',3],
['2024/4/3',3],
['2024/4/7',7],
['2024/4/13',13],
['2024/4/14',14],
['2024/4/19',14],
['2024/4/27',16],
['2024/4/29',29],
['2024/5/20',20],
['2024/5/21',17],
['2024/5/22',16],
['2024/5/26',14],
['2024/5/26',16],
['2024/6/3',15],
['2024/6/3',3],
['2024/6/25',12],
['2024/6/27',14],
['2024/6/28',15],
['2024/6/29',11],
['2024/6/29',13],
['2024/7/12',14],
['2024/7/16',12],
['2024/7/16',17],
['2024/7/20',19],
['2024/7/22',23],
['2024/7/25',25],
['2024/7/28',28],
['2024/7/28',28],
['2024/7/29',29],
['2024/8/1',24],
['2024/8/1',26],
['2024/8/13',13],
['2024/8/18',18],
['2024/8/18',18],
['2024/8/26',34],
['2024/8/27',27],
['2024/8/30',30],
['2024/9/8',35],
['2024/9/11',33],
['2024/9/20',20],
['2024/9/23',23],
['2024/9/23',23],
['2024/9/24',24],
['2024/9/27',27],
['2024/9/27',27],
['2024/9/30',30],
['2024/10/10',10],
['2024/10/11',11],
['2024/10/14',14],
['2024/10/17',17],
['2024/10/23',23],
['2024/10/26',26],
['2024/10/29',29],
['2024/11/2',2],
['2024/11/12',7],
['2024/11/15',9],
['2024/11/19',6],
['2024/11/19',9],
['2024/12/2',2],
['2024/12/15',2],
['2024/12/19',5],
['2024/12/20',2],
['2024/12/22',6],

                    ],
            markArea: {
                silent: true,
                itemStyle: {
                color: 'transparent',
                borderWidth: 1,
                borderType: 'dashed'
                },
                data: [
                [
                    {
                    name: '数通Alarm',
                    xAxis: 'min',
                    yAxis: 'min'
                    },
                    {
                    xAxis: 'max',
                    yAxis: 'max'
                    }
                ]
                ]
            },
            markPoint: {
                data: [
                { type: 'max', name: 'Max' },
                { type: 'min', name: 'Min' }
                ]
            },
            markLine: {
                lineStyle: {
                type: 'solid'
                },
                data: [{ type: 'average', name: 'AVG' }, { xAxis: 160 }]
            }
            },
            {
            name: '传输网',
            type: 'scatter',
            emphasis: {
                focus: 'series'
            },
            // prettier-ignore
            data: [['2024/6/24',15],
                    ['2024/3/19',8],
                    ['2024/12/6',4],
                    ['2024/5/7',8],
                    ['2024/10/22',4],
                    ['2024/11/22',2],
                    ['2024/9/22',10],
                    ['2024/7/9',17],
                    ['2024/1/23',14],
                    ['2024/1/29',8],
                    ['2024/9/22',1],
                    ['2024/3/24',12],
                    ['2024/3/7',13],
                    ['2024/1/13',15],
                    ['2024/7/12',7],
                    ['2024/10/8',1],
                    ['2024/5/8',2],
                    ['2024/6/24',2],
                    ['2024/1/15',20],
                    ['2024/8/20',0],
                    ['2024/3/10',14],
                    ['2024/1/6',15],
                    ['2024/4/28',9],
                    ['2024/6/27',18],
                    ['2024/9/13',12],
                    ['2024/9/25',6],
                    ['2024/5/9',18],
                    ['2024/11/24',4],
                    ['2024/6/3',18],
                    ['2024/6/23',12],
                    ['2024/8/1',20],
                    ['2024/8/17',8],
                    ['2024/5/6',19],
                    ['2024/10/15',0],
                    ['2024/5/22',5],
                    ['2024/1/14',10],
                    ['2024/11/29',14],
                    ['2024/2/5',4],
                    ['2024/11/22',8],
                    ['2024/10/2',15],
                    ['2024/10/1',16],
                    ['2024/8/8',19],
                    ['2024/10/25',5],
                    ['2024/4/12',7],
                    ['2024/7/6',11],
                    ['2024/2/15',20],
                    ['2024/9/1',8],
                    ['2024/10/18',5],
                    ['2024/3/29',10],
                    ['2024/6/19',6],
                    ['2024/3/14',18],
                    ['2024/11/4',2],
                    ['2024/1/24',19],
                    ['2024/3/7',13],
                    ['2024/9/3',14],
                    ['2024/7/26',8],
                    ['2024/7/3',5],
                    ['2024/6/2',9],
                    ['2024/6/14',10],
                    ['2024/2/11',10],
                    ['2024/3/8',3],
                    ['2024/11/6',8],
                    ['2024/4/28',0],
                    ['2024/3/12',5],
                    ['2024/3/24',1],
                    ['2024/12/10',15],
                    ['2024/3/25',14],
                    ['2024/6/12',11],
                    ['2024/8/20',2],
                    ['2024/10/14',5],
                    ['2024/2/1',2],
                    ['2024/3/16',14],
                    ['2024/2/17',5],
                    ['2024/1/26',7],
                    ['2024/7/28',11],
                    ['2024/11/30',0],
                    ['2024/2/14',8],
                    ['2024/12/7',3],
                    ['2024/11/30',1],
                    ['2024/9/10',11],
                    ['2024/6/18',1],
                    ['2024/3/29',11],
                    ['2024/2/19',6],
                    ['2024/6/19',18],
                    ['2024/3/10',15]
                    ],
            markArea: {
                silent: true,
                itemStyle: {
                color: 'transparent',
                borderWidth: 1,
                borderType: 'dashed'
                },
                data: [
                [
                    {
                    name: '传输Alarm',
                    xAxis: 'min',
                    yAxis: 'min'
                    },
                    {
                    xAxis: 'max',
                    yAxis: 'max'
                    }
                ]
                ]
            },
            markPoint: {
                data: [
                { type: 'max', name: 'Max' },
                { type: 'min', name: 'Min' }
                ]
            },
            markLine: {
                lineStyle: {
                type: 'solid'
                },
                data: [{ type: 'average', name: 'Average' }, { xAxis: 170 }]
            }
            }
        ]
    })
}}
</script>
<style>
    .container {  display: grid;
    grid-template-columns: 1fr 1fr 1fr 1fr;
    grid-template-rows: 200x 200x 200x;
    gap: 0px 0px;
    grid-auto-flow: row;
    grid-template-areas:
        "no1 no1 no2 no2"
        "no3 no3 no3 no3"
    }

    .no1 { grid-area: no1; }

    .no2 { grid-area: no2; }

    .no3 { grid-area: no3; }

</style>