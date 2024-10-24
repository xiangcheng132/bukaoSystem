<template>
  <div class="main">
    {{props.isAnswer == false}}
    <div>
      单选题：
      <div class="resources" v-for="(v,i) in props.singleList">
        <p class="title">第{{i+1}}题：{{v.title}}</p>
        <div class="opKS">
            <ul class="options">
              <li v-for="k in v.options">{{k}}</li>
            </ul>
              <div class="key" v-if="props.isAnswer == 'true'">答案：<input type="text" :value="v.userKey" @change="updateKey(props.answerSheetId,v.id,$event.target.value)"></div>
              <div class="score" v-if="props.isAnswer == 'true'">得分：{{v.score}}</div> 
              <div class="key" v-if="props.isAnswer == 'false'">答案：{{v.userKey}}</div>
              <div class="score" v-if="props.isAnswer == 'false'">得分：<input type="text" v-model="v.score" @change="updateKeyScore()"></div>  
         </div>
      </div>
        判断题：  
        <div class="resources" v-for="(v,i) in props.tFList">
          <p class="title">第{{i+1}}题：{{v.title}}</p>
          <div class="opKS">
            <ul class="options">
              <li>&nbsp;对</li>
              <li>&nbsp;错</li>
            </ul>
              <div class="key" v-if="props.isAnswer == 'true'">答案：<input type="text" :value="v.userKey" @change="updateKey(props.answerSheetId,v.id,$event.target.value)"></div>
              <div class="score" v-if="props.isAnswer == 'true'">得分：{{v.score}}</div>
              <div class="key" v-if="props.isAnswer == 'false'">答案：{{v.userKey}}</div>
              <div class="score" v-if="props.isAnswer == 'false'">得分：<input type="text" :value="v.score" @change="updateKeyScore()"></div>
          </div>
        </div>  
        填空题：
        <div class="resources" v-for="(v,i) in props.completionList">
          <p class="title">第{{i+1}}题：{{v.title}}</p>
          <div class="opKS">
              <div class="key" v-if="props.isAnswer == 'true'">答案：<input type="text" :value="v.userKey" @change="updateKey(props.answerSheetId,v.id,$event.target.value)"></div>
              <div class="score" v-if="props.isAnswer == 'true'">得分：{{v.score}}</div>
              <div class="key" v-if="props.isAnswer == 'false'">答案：{{v.userKey}}</div>
              <div class="score" v-if="props.isAnswer == 'false'">得分：<input type="text" :value="v.score" @change="updateKeyScore()"></div>
          </div>
        </div>  
        问答题：
        <div class="resources" v-for="(v,i) in props.bigquestionList">
          <p class="title">第{{i+1}}题：{{v.title}}</p>
          <div class="opKS">
              <div class="key" v-if="props.isAnswer == 'true'">答案：<input type="text" :value="v.userKey" @change="updateKey(props.answerSheetId,v.id,$event.target.value)"></div>
              <div class="score" v-if="props.isAnswer == 'true'">得分：{{v.score}}</div>
              <div class="key" v-if="props.isAnswer == 'false'">答案：{{v.userKey}}</div>
              <div class="score" v-if="props.isAnswer == 'false'">得分：<input type="text" :value="v.score" @change="updateKeyScore()"></div>
          </div>
        </div>  
    </div>
  </div>
</template>
<style lang = 'less' scoped>
ul,
li {
  list-style: none;
}
.main {
  width: 100%;
  border: 1px solid #ccc;
  padding: 10px;
}
.resources {
  padding-bottom: 10px;
}
.options{
  margin: 10px auto;
}
</style>
<script setup>
import { ref, reactive, onMounted, defineProps } from "vue";
import { setStorage, getStorage } from "@/utils/storage.js";
import { useRoute, useRouter } from "vue-router";
import { defineEmits } from 'vue';
import {saveOrUpdateExamAnswerSheetDetail} from "@/api/examAnswerSheetDetail"
const emit = defineEmits(['changeScore']);
const props = defineProps(['singleList','tFList','completionList','bigquestionList','isAnswer','answerSheetId']);
console.log(props);
async function updateKey(answerSheetId,id,value){
  console.log(answerSheetId,id,value);
  //调用更新答卷中间表的接口，提交用户填写的答案  
  const result = await saveOrUpdateExamAnswerSheetDetail({answerId:answerSheetId,resourceId:id,userKey:value})
}
function updateKeyScore(){
   emit('changeScore', {singleList:props.singleList,tFList:props.tFList,completionList:props.completionList,bigquestionList:props.bigquestionList});
}
</script>
