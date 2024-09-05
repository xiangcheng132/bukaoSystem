<template>
  <div class="dashboard-container">
    <el-row :gutter="40" class="panel-group">
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-people">
            <el-icon>
              <Document />
            </el-icon>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              试卷总数
            </div>
            <div class="card-panel-number">
              {{ examPaperCount }}
            </div>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-message">
            <el-icon>
              <Document />
            </el-icon>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              题目总数
            </div>
            <div class="card-panel-number">
              {{ questionCount }}
            </div>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-shopping">
            <el-icon>
              <Document />
            </el-icon>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              答卷总数
            </div>
            <div class="card-panel-number">
              {{ doExamPaperCount }}
            </div>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-money">
            <el-icon>
              <Document />
            </el-icon>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              答题总数
            </div>
            <div class="card-panel-number">
              {{ doQuestionCount }}
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row class="echarts-line">
      <el-col style="background-color: white;">
        <div ref="chartContainer" style="width: 100%; height: 600PX; margin: 40px 30px 30px 30px ; "></div>
      </el-col>
    </el-row>
  </div>
</template>

<!-- <script setup>
import { getExamData,getmounthData } from '@/api/examData';
import * as echarts from 'echarts';
import { ref, onMounted,onUnmounted,watch } from 'vue';
import { useStore } from "vuex";
const store = useStore();

const examPaperCount = ref(0);
const questionCount = ref(0);
const doExamPaperCount = ref(0);
const doQuestionCount = ref(0);
const chartContainer = ref(null);

const updateData =() => {
  getExamData()
    .then((res) => {
      examPaperCount.value = res.data.examPaperCount;
      questionCount.value = res.data.questionCount;
      doExamPaperCount.value = res.data.doExamPaperCount;
      doQuestionCount.value = res.data.doQuestionCount;
    })
};

let intervalId;

onMounted(() => {
  // 初始化数据
  updateData();

  // 设置定时器，每5秒更新一次数据
  intervalId = setInterval(updateData, 10000);

  if (chartContainer.value) {
    const chart = echarts.init(chartContainer.value);
    const option = {
      title: {
        text: '新增用户数量',
        left: 'center',
        textStyle: {
          fontSize: 30
        }
      },
      xAxis: {
        type: 'category',
        data: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
      },
      yAxis: {
        type: 'value'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          data: [150, 230, 224, 218, 135, 147, 260],
          type: 'line'
        }
      ]
    };
    chart.setOption(option);
  }
});

onUnmounted(() => {
  clearInterval(intervalId);
});

</script> -->
<script setup>
import { getExamData, getMonthlyUserData } from '@/api/examData'; // 添加获取月度数据的API
import * as echarts from 'echarts';
import { ref, onMounted, onUnmounted } from 'vue';

const examPaperCount = ref(0);
const questionCount = ref(0);
const doExamPaperCount = ref(0);
const doQuestionCount = ref(0);
const chartContainer = ref(null);
let chart = null;
let intervalId = null;

const updateData = () => {
  getExamData()
    .then((res) => {
      examPaperCount.value = res.data.examPaperCount;
      questionCount.value = res.data.questionCount;
      doExamPaperCount.value = res.data.doExamPaperCount;
      doQuestionCount.value = res.data.doQuestionCount;
    });

  getMonthlyUserData()
    .then((res) => {
      const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
      const values = months.map(month => res.data[month] || 0); // 使用月份名称获取数据
      if (chart) {
        chart.setOption({
          series: [{
            data: values
          }]
        });
      }
    });
};

onMounted(() => {
  // 初始化数据
  updateData();

  // 设置定时器，每10秒更新一次数据
  intervalId = setInterval(updateData, 10000);

  if (chartContainer.value) {
    chart = echarts.init(chartContainer.value);
    const option = {
      title: {
        text: '新增用户数量',
        left: 'center',
        textStyle: {
          fontSize: 30
        }
      },
      xAxis: {
        type: 'category',
        data: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
      },
      yAxis: {
        type: 'value'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          data: [], // 初始为空，后续用 updateData 更新
          type: 'line'
        }
      ]
    };
    chart.setOption(option);
  }
});

onUnmounted(() => {
  clearInterval(intervalId);
  if (chart) {
    chart.dispose();
  }
});

</script>

<style scoped>
.dashboard-container {
  height: 92%;
  padding: 40px 80px;
  background-color: rgb(240, 242, 245);
  position: relative;
}

.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 140px;
    cursor: pointer;
    font-size: 60px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-people {
        background: #40c9c6;
      }

      .icon-message {
        background: #36a3f7;
      }

      .icon-money {
        background: #f4516c;
      }

      .icon-shopping {
        background: #34bfa3
      }
    }

    .icon-people {
      color: #40c9c6;
    }

    .icon-message {
      color: #36a3f7;
    }

    .icon-money {
      color: #f4516c;
    }

    .icon-shopping {
      color: #34bfa3
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 25px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

@media (max-width: 550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;
  }
}

.card-panel-number {
  float: right;
  margin-top: 20px;
  font-size: 30px;
}

.echarts-line {
  background: #fff;
  padding: 16px 16px 0;
  margin-bottom: 32px;
}
</style>
