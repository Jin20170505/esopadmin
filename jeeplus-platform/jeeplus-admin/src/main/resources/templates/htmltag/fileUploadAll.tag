
<input type="hidden" name="${name!}" id="${id!}Id"  value="${value!}"  data-fun="${callback!}"/>
<div class="input-group">
		<input id="${id!}" readonly="readonly"  onclick="jp.lookUploadImage(this);"
               class="${class!'form-control'}" style="${cssStyle!}"/>
		<span class="input-group-append">
			<button type="button"   id="${id!}Button" onclick="${id!}FileDialogOpen();"  class="btn waves-effect waves-light btn-custom ${disabled!} ${hideBtn!'false'=='true' ? 'hide' : ''}"><i class="fa fa-cloud-upload"></i></button>
		</span>
</div>
<label id="${id!}-error" class="error" for="${id!}"></label>
<script type="text/javascript">
	$(document).ready(function () {
        var value = $("input[name='${name!}']").val();
        var urls = value.split("|");
        var label = "";
        for(var i=0; i<urls.length; i++){
          label= label+  decodeURIComponent(urls[i].substring(urls[i].lastIndexOf("/")+1))+",";
        }
        if(label.length > 0){
            label = label.substr(0, label.length-1);
        }
        $("#${id!}").val(label);
    })
    function ${id!}FileDialogOpen() {
        var currentFileValues = $("input[name='${name!}']").val();
        jp.open({
            type: 2,
            area: ['800px', '300px'],
            title:"上传文件",
            auto:true,
            content: "${ctx}/tag/fileUploadAll?fileValues="+encodeURIComponent(currentFileValues)+"&uploadPath=${uploadPath!}"+"&type=${type!}"+"&readonly=${readonly!}"
            +"&fileNumLimit=${fileNumLimit!}"+"&fileSizeLimit=${fileSizeLimit!}"+"&allowedExtensions=${allowedExtensions!}"+"&deniedExtensions=${deniedExtensions!}",
            cancel: function(index, layero){
                var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                var fileNames =iframeWin.contentWindow.getUploadFileNames();//调用保存事件
                var fileValues =iframeWin.contentWindow.getUploadFileValues();//调用保存事件
                var result = iframeWin.contentWindow.getUploadResult();// 获取文件上传结果
                $("#${id!}").val(fileNames);
                $("input[name='${name!}']").val(fileValues);
                var callbackName = $("#${name}Id").attr("data-fun");
                if(callbackName && typeof(eval(callbackName)) == "function"){
                    var tempObject = {};
                    tempObject.fun = eval(callbackName);
                    tempObject.fun(result);
                }
            }
        });
    }

</script>
