<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设备" prop="eleName">
        <el-input
          v-model="queryParams.eleName"
          placeholder="请输入设备"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="型号" prop="eleType">
        <el-input
          v-model="queryParams.eleType"
          placeholder="请输入型号"
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

    <el-table v-loading="loading" :data="todo_eleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="设备" align="center" prop="eleName" />
      <el-table-column label="型号" align="center" prop="eleType" />
      <el-table-column label="组别" align="center" prop="eleGroup" />
      <el-table-column label="单体电池号" align="center" prop="eleGronum" />
      <el-table-column label="内阻2024春" align="center" prop="eleResis" />
      <el-table-column label="电压2024春" align="center" prop="eleVoltage" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['todo:todo_ele:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['todo:todo_ele:remove']">删除</el-button>
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
        <el-form-item label="设备" prop="eleName">
          <el-input v-model="form.eleName" placeholder="请输入设备" />
        </el-form-item>
        <el-form-item label="型号" prop="eleType">
          <el-input v-model="form.eleType" placeholder="请输入型号" />
        </el-form-item>
        <el-form-item label="组别" prop="eleGroup">
          <el-input v-model="form.eleGroup" placeholder="请输入组别" />
        </el-form-item>
        <el-form-item label="单体电池号" prop="eleGronum">
          <el-input v-model="form.eleGronum" placeholder="请输入单体电池号" />
        </el-form-item>
        <el-form-item label="内阻2024春" prop="eleResis">
          <el-input v-model="form.eleResis" placeholder="请输入内阻2024春" />
        </el-form-item>
        <el-form-item label="电压2024春" prop="eleVoltage">
          <el-input v-model="form.eleVoltage" placeholder="请输入电压2024春" />
        </el-form-item>
        <el-form-item label="内阻2023秋" prop="eleResis1">
          <el-input v-model="form.eleResis1" placeholder="请输入内阻2023秋" />
        </el-form-item>
        <el-form-item label="电压2023秋" prop="eleVoltage1">
          <el-input v-model="form.eleVoltage1" placeholder="请输入电压2023秋" />
        </el-form-item>
        <el-form-item label="内阻2023春" prop="eleResis2">
          <el-input v-model="form.eleResis2" placeholder="请输入内阻2023春" />
        </el-form-item>
        <el-form-item label="电压2023春" prop="eleVoltage2">
          <el-input v-model="form.eleVoltage2" placeholder="请输入电压2023春" />
        </el-form-item>
        <el-form-item label="内阻2022秋" prop="eleResis3">
          <el-input v-model="form.eleResis3" placeholder="请输入内阻2022秋" />
        </el-form-item>
        <el-form-item label="电压2022秋" prop="eleVoltage3">
          <el-input v-model="form.eleVoltage3" placeholder="请输入电压2022秋" />
        </el-form-item>
        <el-form-item label="内阻2022春" prop="eleResis4">
          <el-input v-model="form.eleResis4" placeholder="请输入内阻2022春" />
        </el-form-item>
        <el-form-item label="电压2022春" prop="eleVoltage4">
          <el-input v-model="form.eleVoltage4" placeholder="请输入电压2022春" />
        </el-form-item>
        <el-form-item label="内阻2021秋" prop="eleResis5">
          <el-input v-model="form.eleResis5" placeholder="请输入内阻2021秋" />
        </el-form-item>
        <el-form-item label="电压2021秋" prop="eleVoltage5">
          <el-input v-model="form.eleVoltage5" placeholder="请输入电压2021秋" />
        </el-form-item>
        <el-form-item label="内阻2021春" prop="eleResis6">
          <el-input v-model="form.eleResis6" placeholder="请输入内阻2021春" />
        </el-form-item>
        <el-form-item label="电压2021春" prop="eleVoltage6">
          <el-input v-model="form.eleVoltage6" placeholder="请输入电压2021春" />
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
    id: null,
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
  ids.value = selection.map(item => item.id);
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
  const _id = row.id || ids.value
  getTodo_ele(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改电池测试";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["todo_eleRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
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
  const _ids = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除电池测试编号为"' + _ids + '"的数据项？').then(function() {
    return delTodo_ele(_ids);
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
