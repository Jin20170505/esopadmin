
/**
 * 工具组件 对原有的工具进行封装，自定义某方法统一处理
 * 
 * @author lgf 2018-4-10
 * @Url: http://www.jeeplus.org
 * @version 2.0v
 */



(function() {

    jp = {
        _Storage: window.localStorage,
        // 是否支持缓存
        isSupportStorage: function () {
            return this._Storage != undefined ? true : false;
        },
        // 是否有缓存
        hasItem: function (item) {
            return this._Storage.hasOwnProperty(item);
        },
        // 获取缓存 key-value形式
        getItem: function (item) {
            // 将json字符串转成对象或数组
            return JSON.parse(this._Storage.getItem(item));
        },
        // 设置缓存 key-value形式
        setItem: function (item, val) {
            // 将对象或数组转成json字符串
            return this._Storage.setItem(item, JSON.stringify(val));
        },
        tableOnloadSucess:function(tid,field){
                if (!(this.isSupportStorage() && this.hasItem(tid))) {
                    var aTmpConfig = field.split(',');
                    this.setItem(tid, aTmpConfig);
                }
                var aUserConfHideCols = this.getItem(tid);//根据ID获取
                if (aUserConfHideCols != "") {
                    // 遍历用户配置中的数据
                    aUserConfHideCols.forEach(function (sField) {
                        $("#" + tid).bootstrapTable('hideColumn', sField);
                    });
                }
        },
        columnSwitch: function (tid, DomId) {
        // tid表示表格id，必须唯一
        // DomId表示特定id table的父级
        var aTmpConfig = [];
        // 找到特定id下的所有input框，遍历，i表示索引，v表示元素
        $("#" + DomId + " .bootstrap-table  div.dropdown-menu").find("input").each(function (i, v) {
            // 判断未勾选的列，获得字段属性存入定义的数组中
            if (!$(v).is(':checked')) {
                aTmpConfig.push($(v).attr("data-field"));
            }
        });
        // 通过setItem方法，以key-value形式存入缓存中
        this.setItem(tid, aTmpConfig);
    },
	   /**使用jp.open代替top.layer.open，参数使用完全一致，请参照layer官网, 不在直接暴露layer在，jeeplus对layer进行统一封装**/
	   open:top.layer.open,

	   /**通知方法，不阻塞浏览器当前窗口，四个级别 info,warning,error,success，图标不同，其余用法完全相同*/		
	   info:function(msg){
			return top.layer.msg(msg);
	   },
		
	   warning: function(msg){//通知
		  return top.layer.msg(msg, {icon:0});
	   },
	   
	   success:function(msg){
		  return top.layer.msg(msg, {icon:1});
	   },

	   error:function(msg){
		   return top.layer.msg(msg, {icon:2});
	   },

	   
	   //layer之外的另一个选择toast风格消息提示框
		toastr_info: function(msg){
            top.toastr["info"](msg, "通知");
		},
        toastr_warning: function(msg){
            top.toastr["warning"](msg, "警告");
        },
        toastr_success: function(msg){
            top.toastr["success"](msg, "成功");
        },
        toastr_error: function(msg){
            top.toastr["error"](msg, "错误");
        },
	   //页面提示声音
	   voice:function() {
		    var audio = document.createElement("audio");
		    audio.src = ctxStatic+"/common/voice/default.wav";
		    audio.play();
	  },
		//block
		block: function (id, msg) {
        	if(!msg){
                msg = '';
			}

            $(id).block({
                message: '<div class="spinner-border text-custom"  role="status"><span class="sr-only">'+msg+'</span></div>',
                overlayCSS: {
                    backgroundColor: 'white'
                },
                fadeOut:0,
                theme:false,
                css: {
                    border: '0px solid #91d5ff',
                    backgroundColor: 'rgb(255, 255, 255,0)',
					color: 'rgb(255, 255, 255,0)'
                }
            });
        },

		//unblock
        /**
         *
         * @param id
         * @param time 遮盖层 多少毫秒后移除，默认400
         */
		unblock: function (id, time) {
		    if(time == undefined){
                $(id).unblock();
            }else if(time == 0){
                $(id).unblock({fadeOut:0});
            }else {
                setTimeout(function(){
                        $(id).unblock();
                    },time
                )
            }

        },
	   
	   /**加载层，一直阻塞浏览器窗口，必须手动调用close方法关闭*/	   
	   loading:function(msg){
		   if(!msg){
			   msg = '正在提交，请稍等...';
		   }

		  var index = top.layer.msg(msg, {
			  icon: 16
			  ,shade: 0.01,
			  time:999999999//设置超长时间
			});

		  return index;
	   },
	   
	   close:function(index){
		   if(index){
			   top.layer.close(index);
		   }else{
			   top.layer.closeAll();
		   }
		   
	   },



	   
	   /**alert弹出框，阻塞浏览器窗口*/
	   alert:function(msg){
		   top.layer.alert(msg, {
			    area:['auto', 'auto']
			    ,icon: 0
			    ,closeBtn: 0
			    ,anim: 4 //动画类型
			  });
	   },
	   
	   /**询问框，阻塞浏览器窗口*/
	   confirm:function(msg, succFuc, cancelFuc){//msg:询问信息， succFuc：点‘是’调用的函数， errFuc:点‘否’调用的函数
	       top.layer.confirm(msg,
		     {icon: 3, title:'系统提示', btn: ['是','否'] //按钮
		     }, function(index){
		    	 if (typeof succFuc == 'function') {
		    		 succFuc();
		 		}else{
		 			location['data-pjax'] = "data-pjax";
		 			location = succFuc;
		 			jp.success("操作成功！", {icon:1});
		 		}
			     top.layer.close(index);
			 }, function(index){
				 if(cancelFuc)
					 cancelFuc();
			     top.layer.close(index);
			 });
		   
		   return false;
	   },

        prompt:function (title, href) {
            var index = top.layer.prompt({title: title, formType: 2}, function(text){
                if (typeof href == 'function') {
                    href(text);
                }else{
                    location = href + encodeURIComponent(text);
                }

                top.layer.close(index);
            });

        },
        //打开一个窗体
        windowOpen:function(url, name, width, height){
        var top=parseInt((window.screen.height-height)/2,10),left=parseInt((window.screen.width-width)/2,10),
            options="location=no,menubar=no,toolbar=no,dependent=yes,minimizable=no,modal=yes,alwaysRaised=yes,"+
                "resizable=yes,scrollbars=yes,"+"width="+width+",height="+height+",top="+top+",left="+left;
        window.open(url ,name , options);
   	 },

	   /**打开图片预览框**/
	   showPic:function(url){
		   var json = {
				   "data": [   //相册包含的图片，数组格式
				     {
				       "src": url, //原图地址
				     }
				   ]
				 };
		   top.layer.photos({
			    photos: json
			    ,anim: 0 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
			  });
		   
	   },
	   
	   /**用户选择框**/
	   openUserSelectDialog:function(isMultiSelect, yesFuc){
		   top.layer.open({
			    type: 2, 
			    area: ['1200px', '600px'],
			    title:"选择用户",
			    auto:true,
		        maxmin: true, //开启最大化最小化按钮
			    content: ctx+"/sys/user/userSelect?isMultiSelect="+isMultiSelect,
			    btn: ['确定', '关闭'],
			    yes: function(index, layero){
			    	var ids = layero.find("iframe")[0].contentWindow.getIdSelections();
			    	var names = layero.find("iframe")[0].contentWindow.getNameSelections();
			    	var loginNames = layero.find("iframe")[0].contentWindow.getLoginNameSelections();
			    	var rows = layero.find("iframe")[0].contentWindow.getRowsSelections();
					if(ids.length ==0){
						jp.warning("请选择至少一个用户!");
						return;
					}
					if(!isMultiSelect && ids.length > 1){
                        jp.warning("只能选择一个用户!");
                        return;
                    }
			    	// 执行保存
			    	yesFuc(ids.join(","), names.join(","), loginNames.join(","),rows);
			    	
			    	top.layer.close(index);
				  },
				  cancel: function(index){ 
					  //取消默认为空，如需要请自行扩展。
					  top.layer.close(index);
	   	         }
			}); 
	   },

        /**用户选择框**/
        openRoleSelectDialog:function(isMultiSelect, yesFuc){
        	var url = ctx + "/sys/role/data";
        	var fieldLabels = "角色名|英文名";
        	var fieldKeys = "name|enname";
            top.layer.open({
                type: 2,
                area: ['80%', '70%'],
                title:"角色选择",
                auto:true,
                name:'friend',
                content: ctx+"/tag/gridselect?url="+encodeURIComponent(url)+"&fieldLabels="+encodeURIComponent(fieldLabels)+"&fieldKeys="+encodeURIComponent(fieldKeys)+"&searchLabels="+encodeURIComponent(fieldLabels)+"&searchKeys="+encodeURIComponent(fieldKeys)+"&isMultiSelected="+isMultiSelect,
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
                        ids.push(item.id);
                        names.push(item.enname)
                    }
                    yesFuc(ids.join(","), names.join(","));
                    top.layer.close(index);//关闭对话框。
                },
                cancel: function(index){
                }
            });
        },
	   dateFormat:function (timestamp, format) {
          var _this =  new Date(timestamp);
           var o = {
               "M+": _this.getMonth() + 1,
               // month
               "d+": _this.getDate(),
               // day
               "h+": _this.getHours(),
               // hour
               "m+": _this.getMinutes(),
               // minute
               "s+": _this.getSeconds(),
               // second
               "q+": Math.floor((_this.getMonth() + 3) / 3),
               // quarter
               "S": _this.getMilliseconds()
               // millisecond
           };
           if (/(y+)/.test(format) || /(Y+)/.test(format)) {
               format = format.replace(RegExp.$1, (_this.getFullYear() + "").substr(4 - RegExp.$1.length));
           }
           for (var k in o) {
               if (new RegExp("(" + k + ")").test(format)) {
                   format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
               }
           }
           return format;
       },
	    escapeHTML: function(a){  
	        a = "" + a;  
	        return a.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/"/g, "&quot;").replace(/'/g, "&apos;");;  
	    },  
	    /** 
	     * @function unescapeHTML 还原html脚本 < > & " ' 
	     * @param a - 
	     *            字符串 
	     */  
	    unescapeHTML: function(a){  
	        a = "" + a;  
	        return a.replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace(/&amp;/g, "&").replace(/&quot;/g, '"').replace(/&apos;/g, "'");  
	    }, 
	  //获取字典标签
	    getDictLabel : function(data, value, defaultValue){
	    	for (var i=0; i<data.length; i++){
	    		var row = data[i];
	    		if (row.value == value){
	    			return row.label;
	    		}
	    	}
	    	return defaultValue;
	    },

        /*
         */
	    post:function(url,data,callback, errFn){
			$.ajax({
	                url:url,
	                method:"post",
	                data:data,
	                error:function(xhr,textStatus){
	                	if(xhr.status == 0){
	                		jp.info("连接失败，请检查网络!")
	                	}else if(xhr.status == 404){
	                		var errDetail ="<font color='red'>404,请求地址不存在！</font>";
		                	top.layer.alert(errDetail , {
		                		  icon: 2,
		                		  area:['auto','auto'],
		                		  title:"请求出错"
		                	})
	                	}else if(xhr.status && xhr.responseText){
                            var errDetail ="<font color='red'>"+ xhr.responseText.replace(/[\r\n]/g,"<br>").replace(/[\r]/g,"<br>").replace(/[\n]/g,"<br>")+"</font>";
                            top.layer.alert(errDetail , {
                                icon: 2,
                                area:['80%','70%'],
                                title:xhr.status+"错误"
                            })
                        }else{
	                		var errDetail ="<font color='red'>未知错误!</font>";
		                	top.layer.alert(errDetail , {
		                		  icon: 2,
		                		  area:['auto','auto'],
		                		  title:"真悲剧，后台抛出异常了"
		                		})
	                	}
	                	if(errFn){
	                	    errFn();
                        }
	                },
	                success:function(data,textStatus,jqXHR){
	                	if(data.indexOf == "_login_page_"){//登录超时
	                		location.reload(true);          
	                	}else{
	                		callback(data);
	                	}
	                }
           		 });
	    },
	    
	    get:function(url,callback, errFn){
			$.ajax({
	                url:url,
	                method:"get",
	                error:function(xhr,textStatus){
	                	if(xhr.status == 0){
	                		jp.info("连接失败，请检查网络!")
	                	}else if(xhr.status == 404){
	                		var errDetail ="<font color='red'>404,请求地址不存在！</font>";
		                	top.layer.alert(errDetail , {
		                		  icon: 2,
		                		  area:['auto','auto'],
		                		  title:"请求出错"
		                	})
	                	}else if(xhr.status && xhr.responseText){
                            var errDetail ="<font color='red'>"+ xhr.responseText.replace(/[\r\n]/g,"<br>").replace(/[\r]/g,"<br>").replace(/[\n]/g,"<br>")+"</font>";
                            top.layer.alert(errDetail , {
                                icon: 2,
                                area:['80%','70%'],
                                title:xhr.status+"错误"
                            })
                        }else{
	                		var errDetail ="<font color='red'>未知错误!</font>";
		                	top.layer.alert(errDetail , {
		                		  icon: 2,
		                		  area:['auto','auto'],
		                		  title:"真悲剧，后台抛出异常了"
		                		})
	                	}
	                	if(errFn){
	                	    errFn();
                        }
	                	
	                },
	                success:function(data,textStatus,jqXHR){
                        if(data.indexOf == "_login_page_"){//返回首页内容代表登录超时
							top.layer.alert("登录超时！")
	                		location.reload(true);   
	                	}else{
	                		callback(data);
	                	}
	                	
	                }
           		 });
	    },
		/*
		* params:
		*    id: 表单的jQuery ID 例如"#inputForm"
		*    succFuc, 成功后执行的回调函数
		*    beformSubmit：执行提交操作前执行的方法
		 */

		ajaxForm:function(id, succFuc, beforeSubmit){
            $(id).ajaxForm({
                dataType: 'json',
                beforeSubmit: function (arr, form, options) {
                    var isValidate2 = true;
                    if(beforeSubmit){
                        isValidate2 = beforeSubmit();
                    }
                    var isValidate = jp.validateForm(id);//校验表单
                    if(isValidate && isValidate2){
                       //form.find("button:submit").text("loading");
                      //index = jp.loading();
                      jp.block(id);
                    }else{
                        return false;
                    }


                },
                error:function(xhr,textStatus){
                    if(xhr.status == 0){
                        jp.info("连接失败，请检查网络!")
                    }else if(xhr.status == 404){
                        var errDetail ="<font color='red'>404,请求地址不存在！</font>";
                        top.layer.alert(errDetail , {
                            icon: 2,
                            area:['auto','auto'],
                            title:"请求出错"
                        })
                    }else if(xhr.status && xhr.responseText){
                        var errDetail ="<font color='red'>"+ xhr.responseText.replace(/[\r\n]/g,"<br>").replace(/[\r]/g,"<br>").replace(/[\n]/g,"<br>")+"</font>";
                        top.layer.alert(errDetail , {
                            icon: 2,
                            area:['80%','70%'],
                            title:xhr.status+"错误"
                        })
                    }else{
                        var errDetail ="<font color='red'>未知错误!</font>";
                        top.layer.alert(errDetail , {
                            icon: 2,
                            area:['auto','auto'],
                            title:"真悲剧，后台抛出异常了"
                        })
                    }

                    jp.unblock(id);
                   // jp.close(index);
                   // $(id).find("button:submit").button("reset");


                },
                success: function (data, statusText, xhr, form) {
                    jp.unblock(id);
                    succFuc(data);
                    //form.find("button:submit").button("reset");
                    //jp.close(index);
                }
            });

		},

        //打开对话框(添加修改)
        openSaveDialog: function(title,url,width,height){
            var auto = true;//是否使用响应式，使用百分比时，应设置为false
            if(width.indexOf("%")>=0 || height.indexOf("%")>=0 ){
                auto =false;
            }
            top.layer.open({
                type: 2,
                area: [width, height],
                title: title,
                auto:auto,
                maxmin: true, //开启最大化最小化按钮
                content: url ,
                btn: ['确定', '关闭'],
                yes: function(index, layero){
                    var iframeWin = layero.find('iframe')[0]; //得到弹出的窗口对象，执行窗口内iframe页的方法：iframeWin.method();
                    iframeWin.contentWindow.save();//调用保存事件，在 弹出页内，需要定义save方法。处理保存事件。
                    return false;
                },
                cancel: function(index){
                }
            });
        },
        //打开对话框(查看)
        openViewDialog :function(title,url,width,height){
            var auto = true;//是否使用响应式，使用百分比时，应设置为false
            if(width.indexOf("%")>=0 || height.indexOf("%")>=0 ){
                auto =false;
            }
            top.layer.open({
                type: 2,
                area: [width, height],
                title: title,
                auto:auto,
                maxmin: true, //开启最大化最小化按钮
                content: url ,
                btn: ['关闭'],
                cancel: function(index){
                }
            });

        },
        //打开子对话框(仅仅用作 父子layer弹窗之间交互数据使用)

         openChildDialog: function(title,url,width,height, parentObj){
        var auto = true;//是否使用响应式，使用百分比时，应设置为false
        if(width.indexOf("%")>=0 || height.indexOf("%")>=0 ){
        	auto =false;
        }
          	top.layer.open({
          	    type: 2,
          	    area: [width, height],
          	    title: title,
          	    auto:auto,
          	    maxmin: true, //开启最大化最小化按钮
          	    content: url ,
          	    btn: ['确定', '关闭'],
          	    yes: function(index, layero){
          	         var iframeWin = layero.find('iframe')[0]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
          	         iframeWin.contentWindow.save(parentObj);//在子窗口定义save方法，负责实际业务逻辑的执行
                     return false;
          		  },
          	   cancel: function(index){
          	      }
          	});
          },
        validateForm: function (id) {
            return $(id).validate({
                errorPlacement: function(error, element) {
                    if (element.is(":checkbox")||element.is(":radio")){
                        error.appendTo(element.parent().parent().parent().parent());
                    }else  if (element.parent().is(".form_datetime")
                        ||element.parent().is(".input-append")
                        ||element.parent().is(".input-group")
                        ||element.is(".jp-calendar-picker-input")
                        ||element.is(".mydatepicker")){
                        error.appendTo(element.parent().parent());
                    }else  if (element.parent().is(".jp-calendar-picker")){
                        error.appendTo(element.parent().parent());
                    }else{
                        error.insertAfter(element);
                    }
                }
            }).form();

        },
        /**
		 * Ajax 上传文件
         * @param fileObj
         * @param url
         * @param callback
         */
        uploadFile: function (fileObj, url,callback) {
            var data = new FormData(fileObj);
            // data.append("CustomField", "This is some extra data, testing");//如果要添加参数
            $.ajax({
                type: "POST",
                enctype: 'multipart/form-data',
                url: url,
                data: data,
                processData: false, //prevent jQuery from automatically transforming the data into a query string
                contentType: false,
                cache: false,
                timeout: 600000,
                success: function (result) {
                    callback(result);
                },
				error:function(xhr, textStatus){
                    if(xhr.status == 0){
                        jp.info("连接失败，请检查网络!")
                    }else if(xhr.status == 404){
                        var errDetail ="<font color='red'>404,请求地址不存在！</font>";
                        top.layer.alert(errDetail , {
                            icon: 2,
                            area:['auto','auto'],
                            title:"请求出错"
                        })
                    }else if(xhr.status && xhr.responseText){
                        var errDetail ="<font color='red'>"+ xhr.responseText.replace(/[\r\n]/g,"<br>").replace(/[\r]/g,"<br>").replace(/[\n]/g,"<br>")+"</font>";
                        top.layer.alert(errDetail , {
                            icon: 2,
                            area:['80%','70%'],
                            title:xhr.status+"错误"
                        })
                    }else{
                        var errDetail =xhr.responseText=="<font color='red'>未知错误!</font>";
                        top.layer.alert(errDetail , {
                            icon: 2,
                            area:['auto','auto'],
                            title:"真悲剧，后台抛出异常了"
                        })
                    }

				}
        	})
   		 },
        downloadFile: function(url, name) {
			var $a = $("<a></a>").attr("href", url).attr("download", name);
			$a[0].click();
    	},
        /**
		 *
         * @param name
         * @param value  value必须是字符串，不能是JSON，如果是JSON请使用JSON.stringify()转换为字符串
         * @param myDay
         */
        setCookie: function(name,value, myDay){
			var oDate=new Date();
			oDate.setDate(oDate.getDate()+myDay);
			document.cookie=name+'='+encodeURIComponent(value)+'; expires='+oDate;
    	},

        /**
		 *
         * @param name
         * @returns {string}
         */
       getCookie: function(name){
        //document.cookie获取当前网站的所有cookie
			var arr=document.cookie.split('; ');
			for(var i=0;i<arr.length;i++){
				var arr1=arr[i].split('=');
				if(arr1[0]==name){
					return decodeURIComponent(arr1[1]);
				}
			}
			return '';
    	},
        /**
         * @param name
         */
    	removeCookie: function(name){
			setCookie(name,1,-1);
		},

        go:function(url){//在当前选项卡跳转页面
            window.location.href = url;
        },

        openTab:function(url,title, isNew){//isNew 为true时，打开一个新的选项卡；为false时，如果选项卡不存在，打开一个新的选项卡，如果已经存在，使已经存在的选项卡变为活跃状态。
            top.openTab(url,title,isNew);
        },
        /**
         * 返回当前活跃的tab页面关联的iframe的Windows对象，方便layer弹窗调用父页面的方法。
         */
        getParent: function () {
            if(top.getActiveTab()[0]){
                return top.getActiveTab()[0].contentWindow;
            }else{
                return parent;
            }
        },
        /** 图片预览 */
        jqueryView:function(imageStr){
            if(!imageStr){
                return false;
            }
            var imgArr = imageStr.split('|');
            var tempArr = [];
            for(var i=0;i<imgArr.length;i++){
                if(/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(imgArr[i])){
                    tempArr.push(imgArr[i]);
                }
            }
            $('#imageUl')?$('#imageUl').remove():console.log('');
            var _imageUl = $('<ul>').prop('id', 'imageUl').html('imageUl').addClass('hide');
            $('body').append(_imageUl)
            for (item of tempArr) {
                $('#imageUl').append($('<li>').append($('<img>').prop('src', item)));
            }
            $('#imageUl').viewer();
            $('#imageUl img').eq(0).trigger('click')
        },
        lookUploadImage:function (tag) {
            var name = tag.getAttribute("data-id");
            var images = $("input[name='"+name+"']").val();
            this.jqueryView(images);
        }
	}


		
})(jQuery);

