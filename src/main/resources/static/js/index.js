layui.define(['layer', 'laytpl', 'form', 'element', 'upload', 'util'], function(exports){
  
  var $ = layui.jquery
  ,layer = layui.layer
  ,laytpl = layui.laytpl
  ,form = layui.form
  ,element = layui.element
  ,upload = layui.upload
  ,util = layui.util
  ,device = layui.device()
  ,DISABLED = 'layui-btn-disabled';
  
  //阻止IE7以下访问
  if(device.ie && device.ie < 10){
    layer.alert('如果您非得使用 IE 浏览器访问，那么请使用 IE10+');
  }

  //头像
  if(device.android || device.ios){
    $('#LAY_header_avatar').on('click', function(){
      return false;
    })
  }

  //刷新图形验证码
/*  $('body').on('click', '.fly-imagecode', function(){
    this.src = '/auth/imagecode?t='+ new Date().getTime();
  });*/

  //加载特定模块
  if(layui.cache.page && layui.cache.page !== 'index'){
    var extend = {};
    extend[layui.cache.page] = layui.cache.page;
    layui.extend(extend);
    layui.use(layui.cache.page);
  }


  //手机设备的简单适配
  var treeMobile = $('.site-tree-mobile')
  ,shadeMobile = $('.site-mobile-shade')

  treeMobile.on('click', function(){
    $('body').addClass('site-mobile');
  });

  shadeMobile.on('click', function(){
    $('body').removeClass('site-mobile');
  });
  
});

