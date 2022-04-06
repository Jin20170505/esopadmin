	<input id="${id}Id" name="${name}"  type="hidden" value="${value!}" data-fun="${callback!}"data-extendId="${extendId!}"  data-extendTagId="${extendTagId!}" data-extendTagName="${extendTagName!}"/>


	<div class="input-group">
		<input class="form-control ${class!}"  id="${id}Name" name="${labelName!}" ${allowInput!false==true?'':'readonly="readonly"'}  type="text" value="${labelValue!}" data-msg-required="${dataMsgRequired!}" type="text">
		<span class="input-group-append">
			<button type="button"   id="${id}Button" class="btn waves-effect waves-light btn-custom  ${disabled!} ${hideBtn!'false'=='true' ? 'hide' : ''}"><i class="fa fa-search"></i></button>
			  <% if(allowClear!'false' == 'true'){ %>
	             	 <button type="button" id="${id}DelButton" class="close" data-dismiss="alert" style="position: absolute;top: 10px;right: 53px;z-index: 999;display: block;font-size: 18px;">×</button>
	            <% } %>
		</span>
	</div>


	 <label id="${id}Name-error" class="error" for="${id}Name" style="display:none"></label>
<script type="text/javascript">
$(document).ready(function(){
	$("#${id}Button, #${id}Name").click(function(){
		if ($("#${id}Button").hasClass("disabled")){
			return true;
		}
		var extendTagId = $("#${id}Id").attr("data-extendTagId");
		var extendUrl = '';
		if(extendTagId){
            var extendTagName = $("#${id}Id").attr("data-extendTagName");
			var extendId= $('#'+extendTagId).val();
			if(!extendId){
				jp.error('请填写'+extendTagName);
				return false;
            }
            var pidName = $("#${id}Id").attr("data-extendId");
			extendUrl = "?"+pidName+"="+extendId;
        }
		top.layer.open({
		    type: 2,
		    area: ['1000px', '700px'],
		    title:"${title!}",
		    auto:true,
		    name:'friend',
		    content: "${ctx}/tag/gridselect?url="+encodeURIComponent("${url}"+extendUrl)+"&fieldLabels="+encodeURIComponent("${fieldLabels}")+"&fieldKeys="+encodeURIComponent("${fieldKeys}")+"&searchLabels="+encodeURIComponent("${searchLabels!}")+"&searchKeys="+encodeURIComponent("${searchKeys!}")+"&isMultiSelected=${isMultiSelected!false}",
		    btn: ['确定', '关闭'],
		    yes: function(index, layero){
		    	 var iframeWin = layero.find('iframe')[0].contentWindow; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
		    	 var items = iframeWin.getSelections();
		    	 if(items == ""){
			    	jp.warning("必须选择一条数据!");
			    	return;
		    	 }
		    	 var ids = [];
		    	 var names = [];
		    	 for(var i=0; i<items.length; i++){
		    		 var item = items[i];
		    		 ids.push(item${fn.substring(name, fn.lastIndexOf(name, '.'), fn.length(name))});
		    		 names.push(item${fn.substring(labelName, fn.lastIndexOf(labelName, '.'), fn.length(labelName))})
		    	 }
		    	 $("#${id}Id").val(ids.join(","));
		    	 $("#${id}Name").val(names.join(","));
                var callbackFunName = $("#${id}Id").attr("data-fun");
                var tagid = "${id}";
                if(callbackFunName){
                    if(typeof(eval(callbackFunName)) == "function"){
                        var tempObject = {};
                        tempObject.fun = eval(callbackFunName);
                        tempObject.fun(items,tagid);
                    }
                }
				 top.layer.close(index);//关闭对话框。
			  },
			  cancel: function(index){
		       }
		});
	})
	$("#${id}DelButton").click(function(){
		// 是否限制选择，如果限制，设置为disabled
		if ($("#${id}Button").hasClass("disabled")){
			return true;
		}
		// 清除
		$("#${id}Id").val("");
		$("#${id}Name").val("");
		$("#${id}Name").focus();

	});
})
</script>
