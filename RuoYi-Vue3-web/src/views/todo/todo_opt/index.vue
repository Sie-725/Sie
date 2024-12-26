<template>
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
      <el-table-column label="光缆区间" align="center" prop="optStage" />
      <el-table-column label="光缆距离" align="center" prop="optLength" />
      <el-table-column label="光缆衰耗" align="center" prop="optDec" />
      <el-table-column label="备注信息" align="center" prop="optRemark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['todo:todo_opt:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['todo:todo_opt:remove']">删除</el-button>
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
</template>

<script setup name="Todo_opt">
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
    optStage: null,
    optLength: null,
    optDec: null,
    optRemark: null,
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
