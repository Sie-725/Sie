<template>
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
</template>

<script setup name="Todo_roomhealth">
import { listTodo_roomhealth, getTodo_roomhealth, delTodo_roomhealth, addTodo_roomhealth, updateTodo_roomhealth } from "@/api/todo/todo_roomhealth";

const { proxy } = getCurrentInstance();

const todo_roomhealthList = ref([]);
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
