var tableModel = (function () {
    return{
        getHeight : function () {
            return $(window).height() - $('.content-header').outerHeight(true);
        },
        getState : function (value,row,index) {
            return value==1 ? "启用" : "禁用";
        }
    }
})();

var layerModel = (function () {
    return{
        closeParent : function () {
            var current = parent.layer.getFrameIndex(window.name); //获取窗口索引
            parent.layer.close(current);
        }
    }
})();


var uploadModel = (function () {
    return{
        /**
         * 打开文件上传对话框
         * @param dialog_title 对话框标题
         * @param callback 回调方法，参数有（当前dialog对象，选择的文件数组，你设置的extra_params）
         * @param extra_params 额外参数，object
         * @param multi 是否可以多选
         * @param filetype 文件类型，image,video,audio,file
         */
        open_upload_dialog : function (title,callback,extra_params,multi,filetype){
            multi = multi ? 1 : 0;
            filetype = filetype ? filetype : 'image';
            var params = 'multi='+multi+'&fileType='+filetype ;
            layer.open({
                type: 2,
                title: title,
                closeBtn: false,
                shadeClose: true,
                shade: false,
                maxmin: false,
                area: ['500px', '600px'],
                content: '/console/upload/index/?' + params,
                btn: ['确定', '取消'],
                yes : function(index, layero){
                    if (typeof callback =='function') {
                        var iframewindow = $(layero).find('iframe')[0].contentWindow;
                        var files=iframewindow.get_selected_files();
                        if(files){
                            callback.apply(layero,[layero, files]);
                        }else{
                            return false;
                        }
                        layer.close(index);
                    }
                },
                btn2: function(index) {
                    layer.close(index);
                }
            });
        },

        upload_one : function (title,input_selector,filetype,extra_params){
            this.open_upload_dialog(title,function(layero,files){
                $('#'+input_selector).val(files[0].filepath);
            },extra_params,0,filetype);
        },

        upload_one_image: function (title,input_selector,extra_params){
            this.open_upload_dialog(title,function(layero,files){
                $('#'+input_selector).val(files[0].preview_url);
                $('#'+input_selector+'-preview').attr('src',files[0].preview_url);
            },extra_params,0,'image');
        },
        /**
         * 多图上传
         * @param dialog_title 上传对话框标题
         * @param container_selector 图片容器
         * @param item_tpl_wrapper_id 单个图片html模板容器id
         */
        upload_multi_image : function (title,container_selector,item_tpl_wrapper_id,extra_params){
            this.open_upload_dialog(title,function(layero,files){
                var tpl=$('#'+item_tpl_wrapper_id).html();
                var html='';
                $.each(files,function(i,item){
                    var itemtpl= tpl;
                    itemtpl=itemtpl.replace(/\{id\}/g,item.id);
                    itemtpl=itemtpl.replace(/\{url\}/g,item.url);
                    itemtpl=itemtpl.replace(/\{preview_url\}/g,item.preview_url);
                    itemtpl=itemtpl.replace(/\{filepath\}/g,item.filepath);
                    itemtpl=itemtpl.replace(/\{name\}/g,item.name);
                    html+=itemtpl;
                });
                $('#'+container_selector).append(html);

            },extra_params,1,'image');
        }
    }
})();

