const { defineConfig } = require('@vue/cli-service');
module.exports = defineConfig({
  transpileDependencies: true,
  devServer:{
    proxy:{
      //自定义请求的开头
      "/api":{
        //目标地址
        target:"http://localhost:8080",
        //因为/api不是请求的一部分，所以替换；
        pathRewrite:{
          "^/api":"",
        }
      }
    }
  }
});
