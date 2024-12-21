<template>
  <div>
    <el-form ref="formRef" :model="formData" :rules="rules" size="default" label-width="100px">
      <el-row gutter="15">
        <el-col :span="10">
          <el-form-item label="设备选择" prop="field103">
            <el-select v-model="formData.field103" placeholder="请选择设备" clearable :style="{width: '100%'}">
              <el-option v-for="(item, index) in field103Options" :key="index" :label="item.label"
                :value="item.value" :disabled="item.disabled"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="作业日期" prop="field105">
            <el-date-picker v-model="formData.field105" format="YYYY-MM-DD" value-format="YYYY-MM-DD"
              :style="{width: '100%'}" placeholder="请选择作业日期" clearable></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row gutter="15">
        <el-col :span="6">
          <el-form-item label="存在问题" prop="field108" required>
            <el-switch v-model="formData.field108" active-text="是" inactive-text="否"></el-switch>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="是否解决" prop="field110" required>
            <el-switch v-model="formData.field110" active-text="是" inactive-text="否"></el-switch>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="计划处理时间" prop="field107">
        <el-slider :max='30' :step='1' v-model="formData.field107"></el-slider>
      </el-form-item>
      <el-form-item label="问题描述" prop="field106">
        <el-input v-model="formData.field106" type="textarea" placeholder="请输入问题描述"
          :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script setup>
import {
  ElMessage
}
from 'element-plus'
const formRef = ref()
const data = reactive({
  formData: {
    field103: undefined,
    field105: '2024-10-24',
    field108: false,
    field110: false,
    field107: 0,
    field106: undefined,
  },
  rules: {
    field103: [{
      required: true,
      message: '请选择设备',
      trigger: 'change'
    }],
    field105: [{
      required: true,
      message: '请选择作业日期',
      trigger: 'change'
    }],
    field106: [],
  }
})
const {
  formData,
  rules
} = toRefs(data)
const field103Options = ref([{
  "label": "选项一",
  "value": 1
}, {
  "label": "选项二",
  "value": 2
}])
/**
 * @name: 表单提交
 * @description: 表单提交方法
 * @return {*}
 */
function submitForm() {
  formRef.value.validate((valid) => {
    if (!valid) return
    // TODO 提交表单
  })
}
/**
 * @name: 表单重置
 * @description: 表单重置方法
 * @return {*}
 */
function resetForm() {
  formRef.value.resetFields()
}

</script>
<style>
</style>
