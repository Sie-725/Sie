<template>
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
</template>

<script setup name="Todo_alarm">
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
