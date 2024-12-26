<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="24">
        <el-carousel :interval="4000" type="card" height="200px">
          <el-carousel-item v-for="item in 3" :key="item">
            <img :src="imgList[item]" width="100%" height="100%">
          </el-carousel-item>
        </el-carousel>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="8" style="display: flex; justify-content: center; align-items: center;">
        <div class="statistic-card">
          <el-statistic :value="218">
            <template #title>
              <div style="display: inline-flex; align-items: center">
                机房电路数量
                <el-tooltip
                  effect="dark"
                  content="Number of users who logged into the product in one day"
                  placement="top"
                >
                  <el-icon style="margin-left: 4px" :size="12">
                    <Warning />
                  </el-icon>
                </el-tooltip>
              </div>
            </template>
          </el-statistic>
          <div class="statistic-footer">
            <div class="footer-item">
              <span>去年同比</span>
              <span class="red">
                2.4%
                <el-icon>
                  <CaretTop />
                </el-icon>
              </span>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="8" style="display: flex; justify-content: center; align-items: center;">
        <div class="statistic-card" >
          <el-statistic :value="98">
            <template #title>
              <div style="display: inline-flex; align-items: center">
                本月故障数量
                <el-tooltip
                  effect="dark"
                  content="Number of users who logged into the product in one month"
                  placement="top"
                >
                  <el-icon style="margin-left: 4px" :size="12">
                    <Warning />
                  </el-icon>
                </el-tooltip>
              </div>
            </template>
          </el-statistic>
          <div class="statistic-footer">
            <div class="footer-item">
              <span>去年同比</span>
              <span class="green">
                12%
                <el-icon>
                  <CaretBottom />
                </el-icon>
              </span>
            </div>
          </div>
        </div>
      </el-col>
      <el-col :span="8" style="display: flex; justify-content: center; align-items: center;">
        <div class="statistic-card">
          <el-statistic :value="2200" title="New transactions today">
            <template #title>
              <div style="display: inline-flex; align-items: center">
                本月耗电量
              </div>
            </template>
          </el-statistic>
          <div class="statistic-footer">
            <div class="footer-item">
              <span>去年同比</span>
              <span class="red">
                11%
                <el-icon>
                  <CaretTop />
                </el-icon>
              </span>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :sm="24" :lg="24" style="padding-left: 20px">
        <h2>遗留设备告警</h2>
        <el-table :data="alarmData" class="data_table">
          <el-table-column prop="system" label="系统" width="180">
          </el-table-column>
          <el-table-column prop="name" label="设备名称" width="180">
          </el-table-column>
          <el-table-column prop="info" label="告警描述" width="360">
          </el-table-column>
          <el-table-column prop="time" label="告警时间">
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <el-divider />
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <template v-slot:header>
            <div class="clearfix">
              <span>工单记录</span>
            </div>
          </template>
          <el-timeline style="max-width: 600px">
            <el-timeline-item timestamp="2024/12/12" placement="top" color="#0bbd87">
              <el-card>
                <el-collapse>
                  <el-collapse-item title="JNTXD-SJW202465">
                    <div>
                      关于开通庄寨CE01/SCADA-G0/0/14 电力通道的工单
                    </div>
                  </el-collapse-item>
                </el-collapse>
                <p>完成时间 2024/12/12 20:46</p>
              </el-card>
            </el-timeline-item>
            <el-timeline-item timestamp="2024/10/22" placement="top" color="#0bbd87">
              <el-card>
                <el-collapse>
                  <el-collapse-item title="JNTXD-SJW202458">
                    <div>
                      关于开通庄寨CE01/SCADA-G0/0/14 电力通道的工单
                    </div>
                  </el-collapse-item>
                </el-collapse>
                <p>完成时间 2024/10/22 14:35</p>
              </el-card>
            </el-timeline-item>
            <el-timeline-item timestamp="2024/09/01" placement="top" color="#0bbd87">
              <el-card>
                <el-collapse>
                  <el-collapse-item title="JNTXD-SJW202433s">
                    <div>
                      关于开通庄寨CE01/SCADA-G0/0/14 电力通道的工单
                    </div>
                  </el-collapse-item>
                </el-collapse>
                <p>完成时间 2024/09/01 10:11</p>
              </el-card>
            </el-timeline-item>
          </el-timeline>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <template v-slot:header>
            <div class="clearfix">
              <span>段发文件</span>
            </div>
          </template>
          <el-collapse accordion>
            <el-collapse-item title="v3.8.7 - 2023-12-08">
              <ol>
                <li>操作日志记录部门名称</li>
                <li>全局数据存储用户编号</li>
                <li>新增编程式判断资源访问权限</li>
                <li>操作日志列表新增IP地址查询</li>
                <li>定时任务新增页去除状态选项</li>
                <li>代码生成支持选择前端模板类型</li>
                <li>显隐列组件支持复选框弹出类型</li>
                <li>通用排序属性orderBy参数限制长度</li>
                <li>Excel自定义数据处理器增加单元格/工作簿对象</li>
                <li>升级oshi到最新版本6.4.8</li>
                <li>升级druid到最新版本1.2.20</li>
                <li>升级fastjson到最新版2.0.43</li>
                <li>升级pagehelper到最新版1.4.7</li>
                <li>升级commons.io到最新版本2.13.0</li>
                <li>升级element-ui到最新版本2.15.14</li>
                <li>修复五级路由缓存无效问题</li>
                <li>修复外链带端口出现的异常</li>
                <li>修复树模板父级编码变量错误</li>
                <li>修复字典表详情页面搜索问题</li>
                <li>修复内链iframe没有传递参数问题</li>
                <li>修复自定义字典样式不生效的问题</li>
                <li>修复字典缓存删除方法参数错误问题</li>
                <li>修复Excel导入数据临时文件无法删除问题</li>
                <li>修复未登录带参数访问成功后参数丢失问题</li>
                <li>修复HeaderSearch组件跳转query参数丢失问题</li>
                <li>修复代码生成导入后必填项与数据库不匹配问题</li>
                <li>修复Excels导入时无法获取到dictType字典值问题</li>
                <li>优化下载zip方法新增遮罩层</li>
                <li>优化头像上传参数新增文件名称</li>
                <li>优化字典标签支持自定义分隔符</li>
                <li>优化菜单管理类型为按钮状态可选</li>
                <li>优化前端防重复提交数据大小限制</li>
                <li>优化TopNav菜单没有图标svg不显示</li>
                <li>优化数字金额大写转换精度丢失问题</li>
                <li>优化富文本Editor组件检验图片格式</li>
                <li>优化页签在Firefox浏览器被遮挡的问题</li>
                <li>优化个人中心/基本资料修改时数据显示问题</li>
                <li>优化缓存监控图表支持跟随屏幕大小自适应调整</li>
                <li>其他细节优化</li>
              </ol>
            </el-collapse-item>
            <el-collapse-item title="v3.8.6 - 2023-06-30">
              <ol>
                <li>支持登录IP黑名单限制</li>
                <li>新增监控页面图标显示</li>
                <li>操作日志新增消耗时间属性</li>
                <li>屏蔽定时任务bean违规的字符</li>
                <li>日志管理使用索引提升查询性能</li>
                <li>日志注解支持排除指定的请求参数</li>
                <li>支持自定义隐藏属性列过滤子对象</li>
                <li>升级oshi到最新版本6.4.3</li>
                <li>升级druid到最新版本1.2.16</li>
                <li>升级fastjson到最新版2.0.34</li>
                <li>升级spring-boot到最新版本2.5.15</li>
                <li>升级element-ui到最新版本2.15.13</li>
                <li>移除apache/commons-fileupload依赖</li>
                <li>修复页面切换时布局错乱的问题</li>
                <li>修复匿名注解Anonymous空指针问题</li>
                <li>修复路由跳转被阻止时内部产生报错信息问题</li>
                <li>修复isMatchedIp的参数判断产生空指针的问题</li>
                <li>修复用户多角色数据权限可能出现权限抬升的情况</li>
                <li>修复开启TopNav后一级菜单路由参数设置无效问题</li>
                <li>修复DictTag组件value没有匹配的值时则展示value</li>
                <li>优化文件下载出现的异常</li>
                <li>优化选择图标组件高亮回显</li>
                <li>优化弹窗后导航栏偏移的问题</li>
                <li>优化修改密码日志存储明文问题</li>
                <li>优化页签栏关闭其他出现的异常问题</li>
                <li>优化页签关闭左侧选项排除首页选项</li>
                <li>优化关闭当前tab页跳转最右侧tab页</li>
                <li>优化缓存列表清除操作提示不变的问题</li>
                <li>优化字符未使用下划线不进行驼峰式处理</li>
                <li>优化用户导入更新时需获取用户编号问题</li>
                <li>优化侧边栏的平台标题与VUE_APP_TITLE保持同步</li>
                <li>优化导出Excel时设置dictType属性重复查缓存问题</li>
                <li>连接池Druid支持新的配置connectTimeout和socketTimeout</li>
                <li>其他细节优化</li>
              </ol>
            </el-collapse-item>
            <el-collapse-item title="v3.8.5 - 2023-01-01">
              <ol>
                <li>定时任务违规的字符</li>
                <li>重置时取消部门选中</li>
                <li>新增返回警告消息提示</li>
                <li>忽略不必要的属性数据返回</li>
                <li>修改参数键名时移除前缓存配置</li>
                <li>导入更新用户数据前校验数据权限</li>
                <li>兼容Excel下拉框内容过多无法显示的问题</li>
                <li>升级echarts到最新版本5.4.0</li>
                <li>升级core-js到最新版本3.25.3</li>
                <li>升级oshi到最新版本6.4.0</li>
                <li>升级kaptcha到最新版2.3.3</li>
                <li>升级druid到最新版本1.2.15</li>
                <li>升级fastjson到最新版2.0.20</li>
                <li>升级pagehelper到最新版1.4.6</li>
                <li>优化弹窗内容过多展示不全问题</li>
                <li>优化swagger-ui静态资源使用缓存</li>
                <li>开启TopNav没有子菜单隐藏侧边栏</li>
                <li>删除fuse无效选项maxPatternLength</li>
                <li>优化导出对象的子列表为空会出现[]问题</li>
                <li>优化编辑头像时透明部分会变成黑色问题</li>
                <li>优化小屏幕上修改头像界面布局错位的问题</li>
                <li>修复代码生成勾选属性无效问题</li>
                <li>修复文件上传组件格式验证问题</li>
                <li>修复回显数据字典数组异常问题</li>
                <li>修复sheet超出最大行数异常问题</li>
                <li>修复Log注解GET请求记录不到参数问题</li>
                <li>修复调度日志点击多次数据不变化的问题</li>
                <li>修复主题颜色在Drawer组件不会加载问题</li>
                <li>修复文件名包含特殊字符的文件无法下载问题</li>
                <li>修复table中更多按钮切换主题色未生效修复问题</li>
                <li>修复某些特性的环境生成代码变乱码TXT文件问题</li>
                <li>修复代码生成图片/文件/单选时选择必填无法校验问题</li>
                <li>修复某些特性的情况用户编辑对话框中角色和部门无法修改问题</li>
                <li>其他细节优化</li>
              </ol>
            </el-collapse-item>
            <el-collapse-item title="v3.8.4 - 2022-09-26">
              <ol>
                <li>数据逻辑删除不进行唯一验证</li>
                <li>Excel注解支持导出对象的子列表方法</li>
                <li>Excel注解支持自定义隐藏属性列</li>
                <li>Excel注解支持backgroundColor属性设置背景色</li>
                <li>支持配置密码最大错误次数/锁定时间</li>
                <li>登录日志新增解锁账户功能</li>
                <li>通用下载方法新增config配置选项</li>
                <li>支持多权限字符匹配角色数据权限</li>
                <li>页面内嵌iframe切换tab不刷新数据</li>
                <li>操作日志记录支持排除敏感属性字段</li>
                <li>修复多文件上传报错出现的异常问题</li>
                <li>修复图片预览组件src属性为null值控制台报错问题</li>
                <li>升级oshi到最新版本6.2.2</li>
                <li>升级fastjson到最新版2.0.14</li>
                <li>升级pagehelper到最新版1.4.3</li>
                <li>升级core-js到最新版本3.25.2</li>
                <li>升级element-ui到最新版本2.15.10</li>
                <li>优化任务过期不执行调度</li>
                <li>优化字典数据使用store存取</li>
                <li>优化修改资料头像被覆盖的问题</li>
                <li>优化修改用户登录账号重复验证</li>
                <li>优化代码生成同步后值NULL问题</li>
                <li>优化定时任务支持执行父类方法</li>
                <li>优化用户个人信息接口防止修改部门</li>
                <li>优化布局设置使用el-drawer抽屉显示</li>
                <li>优化没有权限的用户编辑部门缺少数据</li>
                <li>优化日志注解记录限制请求地址的长度</li>
                <li>优化excel/scale属性导出单元格数值类型</li>
                <li>优化日志操作中重置按钮时重复查询的问题</li>
                <li>优化多个相同角色数据导致权限SQL重复问题</li>
                <li>优化表格上右侧工具条（搜索按钮显隐&右侧样式凸出）</li>
                <li>其他细节优化</li>
              </ol>
            </el-collapse-item>
            <el-collapse-item title="v3.8.3 - 2022-06-27">
              <ol>
                <li>新增缓存列表菜单功能</li>
                <li>代码生成树表新增(展开/折叠)</li>
                <li>Excel注解支持color字体颜色</li>
                <li>新增Anonymous匿名访问不鉴权注解</li>
                <li>用户头像上传限制只能为图片格式</li>
                <li>接口使用泛型使其看到响应属性字段</li>
                <li>检查定时任务bean所在包名是否为白名单配置</li>
                <li>添加页签openPage支持传递参数</li>
                <li>用户缓存信息添加部门ancestors祖级列表</li>
                <li>升级element-ui到最新版本2.15.8</li>
                <li>升级oshi到最新版本6.1.6</li>
                <li>升级druid到最新版本1.2.11</li>
                <li>升级fastjson到最新版2.0.8</li>
                <li>升级spring-boot到最新版本2.5.14</li>
                <li>降级jsencrypt版本兼容IE浏览器</li>
                <li>删除多余的salt字段</li>
                <li>新增获取不带后缀文件名称方法</li>
                <li>新增获取配置文件中的属性值方法</li>
                <li>新增内容编码/解码方便插件集成使用</li>
                <li>字典类型必须以字母开头，且只能为（小写字母，数字，下滑线）</li>
                <li>优化设置分页参数默认值</li>
                <li>优化对空字符串参数处理的过滤</li>
                <li>优化显示顺序orderNum类型为整型</li>
                <li>优化表单构建按钮不显示正则校验</li>
                <li>优化字典数据回显样式下拉框显示值</li>
                <li>优化R响应成功状态码与全局保持一致</li>
                <li>优化druid开启wall过滤器出现的异常问题</li>
                <li>优化用户管理左侧树型组件增加选中高亮保持</li>
                <li>优化新增用户与角色信息&用户与岗位信息逻辑</li>
                <li>优化默认不启用压缩文件缓存防止node_modules过大</li>
                <li>修复字典数据显示不全问题</li>
                <li>修复操作日志查询类型条件为0时会查到所有数据</li>
                <li>修复Excel注解prompt/combo同时使用不生效问题</li>
                <li>其他细节优化</li>
              </ol>
            </el-collapse-item>
            <el-collapse-item title="v1.0.0 - 2019-10-08">
              <ol>
                <li>若依前后端分离系统正式发布</li>
              </ol>
            </el-collapse-item>
          </el-collapse>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <template v-slot:header>
            <div class="clearfix">
              <span>图片新闻</span>
            </div>
          </template>
          <div class="body">
            <img
              src="@/assets/images/jntxd.jpeg"
              alt="donate"
              style="width:100%"
            />
            <span style="display: inline-block; text-align: center; width: 100%; height: 30px; line-height: 30px"
              >济南传输车间设备扩容</span
            >
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup name="Index">
const imgList=["","src/assets/images/1.jpg","src/assets/images/2.jpg","src/assets/images/3.jpg"]
const version = ref('3.8.7')
const alarmData = [{
          system: '数据网',
          name: '菏泽南AR01',
          info: 'G0/0/1#VCS93#收光低#-22.02dBm',
          time: '2024-12-010'
        }, {
          system: '数据网',
          name: '庄寨-CE01/SCADA',
          info: 'G0/0/24#SCADA109#收光低#-21.52dBm',
          time: '2024-12-010'
        }, {
          system: '数据网',
          name: '庄寨-CE01/SCADA',
          info: 'G0/0/14#SCADA109#断开#Down',
          time: '2024-12-010'
        }, {
          system: '数据网',
          name: '曹县AR01',
          info: 'G0/1/5#DCQK50#收光低#-21.52dBm',
          time: '2024-12-010'
        }, {
          system: '数据网',
          name: '巨野AR02',
          info: 'G0/2/4#TMIS300#收光低#-21.52dBm',
          time: '2024-12-010'
        }]
function goTarget(url) {
  window.open(url, '__blank')
}
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}

:global(h2#card-usage ~ .example .example-showcase) {
  background-color: var(--el-fill-color) !important;
}

.el-statistic {
  --el-statistic-content-font-size: 28px;
}

.statistic-card {
  height: 100%;
  padding: 20px;
  border-radius: 4px;
  background-color: var(--el-bg-color-overlay);
}

.statistic-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  font-size: 12px;
  color: var(--el-text-color-regular);
  margin-top: 16px;
}
.st

.statistic-footer .footer-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.statistic-footer .footer-item span:last-child {
  display: inline-flex;
  align-items: center;
  margin-left: 4px;
}

.green {
  color: var(--el-color-success);
}
.red {
  color: var(--el-color-error);
}
</style>

