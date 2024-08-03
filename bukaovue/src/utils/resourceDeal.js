/* 
一、处理 options 数据
    1.遍历数组 
    2.遍历对象拿到 key 和 value
    3.新建对象，将 key 和 value 添加到对象里
    4.将对象 push 到 item 数组
    5.将只有key的空对象赋值给form
二、处理type数据
三、处理所属课程
四、处理所属章节
  */
import {
  getAllCourseByCId
} from "@/api/examCourse";
import {
  getExamCourseChapterById
} from "@/api/examCourseChapter";

// 对资源信息中的选项信息进行处理
export function dealResourceOptionsInfo(data) {
  console.log("1.进入dealResourceOptionsInfo资源处理");
  let tempItem = [];
  data.forEach(function (item, index, arr) {
    // console.log(item);
    let tempForm = {};
    for (let key1 in item) {
      // console.log(key1);
      tempForm[`${key1}`] = item[key1]; // 遍历拿到的资源信息的 key 和 value 遍历到临时表单
    }
    for (let key2 in item.options) {
      // console.log(key," ",item.options[key]);
      // console.log(key2);
      tempForm[`${key2}`] = item.options[key2]; // 遍历拿到 options 信息的遍历 ke y和 value 遍历到临时表单
    }
   tempForm.type = dealResourceTypeInfo(tempForm.type);
   dealResourceCourseInfo(tempForm.courseId).then(res=>{
    // console.log(res);
      tempForm.courseName = res;
   });
   
   dealResourceChapterInfo(tempForm.chapterId).then(res=>{
    // console.log(res);
      tempForm.chapterName = res;
   });
    tempItem.push(tempForm); // 将临时表单添加到临时 item 数组
  });
  console.log(tempItem);
  return tempItem; 
}
// 将 type 值转为中文
// 'single_choice','true_false','completion','bigquestion'
export function dealResourceTypeInfo(type) {
  // console.log("1.进入dealResourceTypeInfo资源处理");
  if (type == "single_choice") {
    type = '单选题'
  } else if (type == "true_false") {
    type = "判断题"
  } else if (type == "completion") {
    type = "填空题"
  } else {
    type = "问答题"
  }
  // console.log("2.处理后返回的type ", type);
  return type;
}
// 处理所属课程
export function dealResourceCourseInfo(courseId) {
  // console.log("1.进入dealResourceCourseInfo资源处理");
  // console.log(courseId);
   let name = getAllCourseByCId(courseId).then(res=>{
  //  console.log("根据课程id获取课程名",res.data.name);
   return res.data.name;
  })
  return name;
}
// 处理所属章节
export function dealResourceChapterInfo(chapterId) {
  // console.log("1.进入dealResourceChapterInfo资源处理");
   let name = getExamCourseChapterById(chapterId).then(res=>{
  //  console.log("根据文章id获取文章名",res.data.name);
    return res.data.name;
  })
  // console.log(name);
  return name;
}
//将选项转为 json 格式数据
export function dealResourceOptions(options){
  console.log("处理前的options",options,"--处理后的：",JSON.stringify(options));
  return JSON.stringify(options);  
}
// 将 type 值转为英文
export function typeReserve(type){
  if (type == "单选题") {
    type = 'single_choice'
  } else if (type == "判断题") {
    type = "true_false"
  } else if (type == "填空题") {
    type = "completion"
  } else {
    type = "bigquestion"
  }
  console.log("2.处理后返回的type ", type);
  return type;
  
}