	<input id="${id}Id" name="${name}"  type="hidden" value="${value!}" data-fun="${callback!}" data-del="${delcall!}"/>


	<div class="input-group">
		<input class="form-control"  id="${id}Name" name="${labelName!}" ${allowInput!true==true?'':'readonly="readonly"'}  type="text" value="${labelValue!}" data-msg-required="${dataMsgRequired!}" type="text">
		<span class="input-group-append">
			<button type="button"   id="${id}Button" class="btn waves-effect waves-light btn-custom  ${disabled!} ${hideBtn!'false'=='true' ? 'hide' : ''}"><i class="fa fa-search"></i></button>
			  <% if(allowClear!'false' == 'true'){ %>
	             	 <button type="button" id="${id}DelButton" class="close" data-dismiss="alert" style="position: absolute;top: 10px;right: 53px;z-index: 999;display: block;font-size: 18px;">×</button>
	            <% } %>
		</span>
	</div>

	 <label id="${id}Name-error" class="error" for="${id}Name" style="display:none"></label>
<script type="text/javascript">
	$("#${id}Button, #${id}Name").click(function(){
		// 是否限制选择，如果限制，设置为disabled
		if ($("#${id}Button").hasClass("disabled")){
			return true;
		}
		// 正常打开	
		
		jp.openUserSelectDialog(${isMultiSelected!false},function(ids, names,loginnames,rows){
			$("#${id}Id").val(ids.replace(/u_/ig,""));
			$("#${id}Name").val(names);
			$("#${id}Name").focus();
			var callbackFunName = $("#${id}Id").attr("data-fun");
			var tagid = "${id}";
			if(callbackFunName){
				if(typeof(eval(callbackFunName)) == "function"){
					var tempObject = {};
					tempObject.fun = eval(callbackFunName);
					tempObject.fun(rows,tagid);
				}
			}
		})
	
	});
	
	$("#${id}DelButton").click(function(){
		// 是否限制选择，如果限制，设置为disabled
		if ($("#${id}Button").hasClass("disabled")){
			return true;
		}
		// 清除	
		$("#${id}Id").val("");
		$("#${id}Name").val("");
		$("#${id}Name").focus();
		var callbackFunName = $("#${id}Id").attr("data-del");
		var tagid = "${id}";
		if(callbackFunName){
			if(typeof(eval(callbackFunName)) == "function"){
				var tempObject = {};
				tempObject.fun = eval(callbackFunName);
				tempObject.fun(tagid);
			}
		}
	
	});
</script>