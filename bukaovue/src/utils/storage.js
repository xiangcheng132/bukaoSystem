// 将 token 存储到系统缓存日志的api
export function setStorage(key, value) {
  // console.log(key,value);
  if( typeof(value)==="object" && value != null){
    value = JSON.stringify(value)
  }
  window.localStorage.setItem(key, value);
}

// 获取缓存日志
export function getStorage(key) {
  let data = window.localStorage.getItem(key);
  try{
    return JSON.parse(data);
  }
  catch(err){
    return data;
  }
}


export function removeStorge(key){
  window.localStorage.removeItem(key);
}


export function clearStorge(key){
  window.localStorage.clear();
}