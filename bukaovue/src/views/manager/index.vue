<template>
  <div>
      管理端首页
  </div>
   <router-view/>
</template>
    
<script>
layui.use(['form'], function(){
                form.on('select(returnType)',function(date){
                debugger
                });
                form.on('select');
                form=layui.form;
                form.render();
                form.on('select(returnType)',function(data){
                    if(data.value=='1'){
                        var html3="<option value='" + ''+ "'>" + '全部' + "</option>"
                        for (var i = 0; i < orderStateArrayEco.length; i++) {
                            html3 = html3 + "<option  value='" + orderStateArrayEco[i].CODE_VALUE + "'>" + orderStateArrayEco[i].CODE_NAME + "</option>";
                        }
                        $("#orderState").html(html3);
                        layui.use('form', function () {
                            form = layui.form;
                            form.render();
                        });
                    }else{
                        var html3="<option value='" + ''+ "'>" + '全部' + "</option>"
                        for (var i = 0; i < orderStateArrayNoEco.length; i++) {
                            html3 = html3 + "<option  value='" + orderStateArrayNoEco[i].CODE_VALUE + "'>" + orderStateArrayNoEco[i].CODE_NAME + "</option>";
                        }
                        $("#orderState").html(html3);
                        layui.use('form', function () {
                            form = layui.form;
                            form.render();
                        });
                    }
                })})



export default {
    
}
</script>
    
<style lang = 'less'>
</style>