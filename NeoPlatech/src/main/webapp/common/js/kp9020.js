
function fnInitTreeData(){

	fnLoadingS2();

	//그룹 트리 구조 생성
	$('#treeData input[name="kp9020TreeData"]').each(function(){
		var spt = $(this).val().split("|");

		if( spt.length == 5 ){
			//그룹 존재여부 확인하여 없으면 등록
			if( $('#KP_TREE_'+spt[0]).length == 0 ){
				//root check1
				if( spt[2] == "" ){
					$('#tree').append('<ul><li id="KP_TREE_'+ spt[0] +'" customcode="" rel="root"><a href="#" title="'+ spt[1] +'">'+ spt[1] +'</a></li></ul>');
				}
				//parent
				if( $('#KP_TREE_'+spt[2]+' ul').length == 0 ){
					$('#KP_TREE_'+spt[2]).append('<ul id="KP_UL_'+ spt[2] +'"></ul>');
				}
				//this
				$('#KP_UL_'+ spt[2]).append('<li id="KP_TREE_'+ spt[0] +'" customcode="'+ spt[2] +'" rel="'+ (spt[4]=="Y"?"folder":"delete") +'"><a href="#" title="'+ spt[1] +'">'+ spt[1]+'</a></li>');
			}
		}
	});
}

//트리구조 생성 ( jstree )
function fnInitTree(nodeOpenYn){

	var jstree = $('#tree').jstree({
		"plugins" : [ "themes","html_data","ui","search","types" ],
		"types" : {
			"types" : {
				"default" : {
					"valid_children" : "none",
					"icon" : {
						"image" : "/common/jstree/_demo/file.png"
					}
				},
				"folder" : {
					"valid_children" : [ "default", "folder" ],
					"icon" : {
						"image" : "/common/jstree/_demo/folder.png"
					}
				},
				"root" : {
					"valid_children" : [ "default", "folder" ],
					"icon" : {
						"image" : "/common/jstree/_demo/root.png"
					},
					"start_drag" : false,
					"move_node" : false,
					"delete_node" : false,
					"remove" : false
				}
			}
		}
	});

	// 트리 전체 View
	jstree.bind("loaded.jstree", function (event, data) {
		if (nodeOpenYn == "Y") {
			//전체 트리 오픈
			data.inst.open_all();
		} else {
			//1depth 트리 오픈
			var depth = 1;
	        data.inst.get_container().find('li').each(function(i) {
	                if(data.inst.get_path($(this)).length<=depth){
	                        data.inst.open_node($(this));
	                }
	        });
		}

		fnLoadingE();
	});

	// onclick 이벤트와 동일
	jstree.bind("select_node.jstree", function (event, data) {
		var select_node = data.rslt.obj.attr("id");
		var cd = select_node.replace("KP_TREE_", "");
		var nm = data.inst.get_text(data.rslt.obj);
		var pcd = data.rslt.obj.attr("customcode");
		nm = nm.replace(/^.+\]/gi, "").trim(); //코드명 앞에 붙어있는 코드값 지우기

		if (cd != "" && cd != "00000") {
			var obj = {
					cd : cd,
					nm : nm,
					pcd : pcd
			}

			opener.fnSetKp9020(obj);
			window.close();
		}

	});

	fnInitSize();
}

function fnInitSize() {
	// 리사이징
	var screen_width = $(window).width();
	var screen_height = $(window).height();
	var left_width = parseInt(screen_width / 100 * 96);
	var left_height = parseInt(screen_height - 160);
	$('#treeDiv').css("width", left_width + "px");
	$('#treeDiv').css("height", left_height + "px");
}
