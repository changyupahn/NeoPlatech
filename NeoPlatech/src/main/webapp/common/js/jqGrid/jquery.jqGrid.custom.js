

(function($){

	var p = $.extend(true,{
		/* Grid 최초 로딩여부 */
		firstLoad:true,
		/* 마지막 선택 ROW Index */
		lastRow:-1,
		/* 첫번째 레코드의 체크박스 비활성 및 setSelection 처리시 미선택 되도록 처리 */
		isFstChkVisible:false
	});
	var ggridId, giRow, giCol, gColLength; 
	
	
$.jgrid.extend({
	/*
	 * jqGrid Ver 4.3.1의 setGroupHeaders customizing
	 * 변경전 : 2단 Header 지원 -> 변경후 : 다단 Header 지원
	 * numberOfColumns: 2 -> 바로 아래 Row에서 처리될 Colspan의 값
	 * skipInit: 1 -> 위의 numberOfColumns + skipInit가 실제 ColModels이 ColSpan대상 Column개수
	 * 예)
	 * jQuery("#nclList").destroyGroupHeader(true);
		jQuery("#nclList").setGroupHeaders({
			useColSpanStyle: true, 
			groupHeaders:[
							[		      		
								{startColumnName: 'upperMenuId', numberOfColumns: 2, skipInit: 0, titleText: 'Menu ID Info'}
							]
							,
							[
								{startColumnName: 'upperMenuId', numberOfColumns: 2, skipInit: 1,  titleText: 'Menu Info'}
							]
						]
		});
	 */
	setGroupHeaders : function ( o ) {
		o = $.extend({
			useColSpanStyle :  false,
			groupHeaders: [,]
		},o  || {});
		return this.each(function(){	
			this.p.groupHeader = o;	
			
			var ts = this,
			grid = ts.grid,
			i, cmi, skip = 0, $colHeader, th, $tr, $th, thStyle,
			iCol,
			cghi,
			//startColumnName,
			numberOfColumns,
			titleText,
			cVisibleColumns,
			colModel = ts.p.colModel,
			cml = colModel.length,
			ths = ts.grid.headers,
			// 00. table 추출
			$htable = $("table.ui-jqgrid-htable", ts.grid.hDiv),
			// 01. thead의 labels 추출 (현재는 last)
			$trLabels = $htable.children("thead").children("tr.ui-jqgrid-labels:first").addClass("jqg-second-row-header"),
			$thead = $htable.children("thead"),
			$theadInTable,
			originalResizeStop,
			// 02. jqg-first-row-header 추출
			$firstHeaderRow = $htable.find(".jqg-first-row-header");
			var k = 0, tmp = [];
			
			
			//  03. jqg-first-row-header이 있으면 초기화, 없으면 $firstHeaderRow 구성
			if($firstHeaderRow.html() === null) {
				$firstHeaderRow = $('<tr>', {role: "row", "aria-hidden": "true"}).addClass("jqg-first-row-header");//.css("height", "auto");
			} else {
				//$firstHeaderRow.empty();
				$firstHeaderRow = $('<tr>', {role: "row", "aria-hidden": "true"}).addClass("jqg-first-row-header");//.css("height", "auto");
			}
			var isMSIE = $.browser.msie ? true:false,
					isSafari = $.browser.webkit || $.browser.safari ? true : false;
			var $firstRow,
			getOffset = function (iCol) {
				var i, ret = {}, brd1 = isSafari ? 0 : ts.p.cellLayout;
				
				// 크롬의 경우 아래로직으로 처리하면 Grid 깨짐..
				// brd=$.browser.webkit||$.browser.safari? 0: $t.p.cellLayout
				// 크롬도 다른 Browser 처럼 처리
				brd1 = ts.p.cellLayout;
				
				ret[0] =  ret[1] = ret[2] = 0;
				for(i=0;i<=iCol;i++){
					if(ts.p.colModel[i].hidden === false ) {
						ret[0] += ts.p.colModel[i].width+brd1;
					}
				}
				if(ts.p.direction=="rtl") { ret[0] = ts.p.width - ret[0]; }
				ret[0] = ret[0] - ts.grid.bDiv.scrollLeft;
				if($(ts.grid.cDiv).is(":visible")) {ret[1] += $(ts.grid.cDiv).height() +parseInt($(ts.grid.cDiv).css("padding-top"),10)+parseInt($(ts.grid.cDiv).css("padding-bottom"),10);}
				if(ts.p.toolbar[0]===true && (ts.p.toolbar[1]=='top' || ts.p.toolbar[1]=='both')) {ret[1] += $(ts.grid.uDiv).height()+parseInt($(ts.grid.uDiv).css("border-top-width"),10)+parseInt($(ts.grid.uDiv).css("border-bottom-width"),10);}
				if(ts.p.toppager) {ret[1] += $(ts.grid.topDiv).height()+parseInt($(ts.grid.topDiv).css("border-bottom-width"),10);}
				ret[2] += $(ts.grid.bDiv).height() + $(ts.grid.hDiv).height();
				return ret;
			},
			inColumnHeader = function (text, columnHeaders) {
				var i = 0, length = columnHeaders.length;
				for (; i < length; i++) {					
					if (columnHeaders[i].startColumnName === text) {
						return i;
					}
				}
				return -1;
			};

			
			//$tr = $('<tr>', {role: "rowheader"}).addClass("jqgfirstrow ui-jqgrid-labels jqg-third-row-header");
			// MultiHeader처리 보완 (height를 auto로 변경)
			//var tmpMsg = "";
			
			for(  j = 0; j < o.groupHeaders.length; j++ )
			{
				/*
				tmpMsg = "[";
				for( kk = 0; kk < o.groupHeaders[j].length; kk++ )
				{
					tmpMsg += "{startColumnName:" + o.groupHeaders[j][kk].startColumnName + ", numberOfColumns : " + o.groupHeaders[j][kk].numberOfColumns + ", skipInit: " + o.groupHeaders[j][kk].skipInit + ", titleText: " + o.groupHeaders[j][kk].titleText + "},";
				}
				tmpMsg += "],";
				
				//alert(tmpMsg);
				*/
				$htable = $("table.ui-jqgrid-htable", ts.grid.hDiv);
				var $trLabels1 = $htable.children("thead").children("tr.ui-jqgrid-labels:first");
				$trLabels = $htable.children("thead").children("tr.ui-jqgrid-labels:first").addClass("jqg-second-row-header");
				$thead = $htable.children("thead");
				$tr = $('<tr>', {role: "rowheader"}).addClass("jqgfirstrow ui-jqgrid-labels jqg-third-row-header");//.css({'height':'auto'});
				k = 0;
				$(ts).prepend($thead);
				if( tmp.length > 0 )
				{
					colModel = [];				
					colModel = tmp;
					cml = colModel.length
					tmp = [];
				}
				
				var intHeaderWithBrTag = 0;
				var headerHeightDefault = 25;
				if( $.browser.msie && $.browser.version > 8 ) {
					headerHeightDefault = 23;
				}
				// 이거 colmodel->trlabels의 값으로 loop 처리해야함.
				//for (i = 0; i < cml; i++) {
				$('th', $trLabels1).each(function(i) {
					//alert($(this).html());
					th = this;
					$th = $(th);
					
					
					cmi = colModel[i];
					// build the next cell for the first header row
					if( j == 0 )
					{
						var tmpWidth = isSafari ? cmi.hidden ? 0 : ths[i].width + 1 : cmi.hidden ? 0 : ths[i].width;
						thStyle = { height: '0px', width: tmpWidth + 'px', display: (cmi.hidden ? 'none' : '')};
						$("<th>", {role: 'gridcell'}).css(thStyle).addClass("ui-first-th-"+ts.p.direction).appendTo($firstHeaderRow);
					}

					//th.style.width = ""; // remove unneeded style
					iCol = inColumnHeader(cmi.name, o.groupHeaders[j]);							
					if (iCol >= 0) {
						tmp[k++] = colModel[i];
						cghi = o.groupHeaders[j][iCol];
						numberOfColumns = cghi.numberOfColumns;
						var skipInit = cghi.skipInit;
						titleText = cghi.titleText;
						
						if( titleText.indexOf("<BR>") != -1 )
						{
							intHeaderWithBrTag = titleText.split("<BR>").length;
						}
						else
							intHeaderWithBrTag = 0;
						
						var colspan = skipInit + numberOfColumns;
						if( titleText == "BLANK")
						{
							titleText = "";
							$colHeader = $('<th>').attr({role: "columnheader", srcColIdx:cghi.srcColIdx})
							.addClass("ui-state-default ui-th-column-header ui-th-"+ts.p.direction)
							.css({'word-wrap':'break-word', 'border': '0px none', 'border-bottom': '1px solid #afafaf', 'border-top': '0px', 'border-right': '1px solid #afafaf'})							
							.html("<span class='ui-jqgrid-resize ui-jqgrid-resize-ltr' style='!important; cursor: e-resize;'>&nbsp;</span>" +
									"<DIV style='WORD-WRAP: break-word;' class='ui-th-div-ie ui-jqgrid-sortable'>" + titleText + "<SPAN style='DISPLAY: none' class=s-ico><SPAN class='ui-grid-ico-sort ui-icon-asc ui-state-disabled ui-icon ui-icon-triangle-1-n ui-sort-ltr' sort='asc'></SPAN><SPAN class='ui-grid-ico-sort ui-icon-desc ui-state-disabled ui-icon ui-icon-triangle-1-s ui-sort-ltr' sort='desc'></SPAN></SPAN></DIV>");
							
							if( cghi.srcColIdx != null && typeof(cghi.srcColIdx) != 'undefined' )
							{
								$colHeader.mousedown(function(e) {
									if ($(e.target).closest("th>span.ui-jqgrid-resize").length != 1) { return; }
									grid.dragStart($(this).attr("srcColIdx"), e, getOffset($(this).attr("srcColIdx")));
									return false;
								}).mouseup(function (e1) {
									if(grid.resizing) {	grid.dragEnd();return false;}
									return true;
								});
							}
						}
						else
						{
							
							if( intHeaderWithBrTag > 0 )
							{
								$colHeader = $('<th>').attr({role: "columnheader", srcColIdx:cghi.srcColIdx})
								.addClass("ui-state-default ui-th-column-header ui-th-"+ts.p.direction)
								.css({'word-wrap':'break-word', 'height':'auto', 'border': '0px none', 'border-bottom': '1px solid #afafaf', 'border-right': '1px solid #afafaf'})
								.html("<span class='ui-jqgrid-resize ui-jqgrid-resize-ltr' style='height:auto;!important; cursor: e-resize;'>&nbsp;</span>" +
										"<DIV style='WORD-WRAP: break-word; HEIGHT:auto;' class='ui-th-div-ie ui-jqgrid-sortable fb'>" + titleText + "<SPAN style='DISPLAY: none' class=s-ico><SPAN class='ui-grid-ico-sort ui-icon-asc ui-state-disabled ui-icon ui-icon-triangle-1-n ui-sort-ltr' sort='asc'></SPAN><SPAN class='ui-grid-ico-sort ui-icon-desc ui-state-disabled ui-icon ui-icon-triangle-1-s ui-sort-ltr' sort='desc'></SPAN></SPAN></DIV>");
							}	
							else
							{
								$colHeader = $('<th>').attr({role: "columnheader", srcColIdx:cghi.srcColIdx})
								.addClass("ui-state-default ui-th-column-header ui-th-"+ts.p.direction)
								.css({'word-wrap':'break-word', 'border': '0px none', 'border-bottom': '1px solid #afafaf', 'border-right': '1px solid #afafaf'})
								.html("<span class='ui-jqgrid-resize ui-jqgrid-resize-ltr' style='!important; cursor: e-resize;'>&nbsp;</span>" +
										"<DIV style='WORD-WRAP: break-word;' class='ui-th-div-ie ui-jqgrid-sortable fb'>" + titleText + "<SPAN style='DISPLAY: none' class=s-ico><SPAN class='ui-grid-ico-sort ui-icon-asc ui-state-disabled ui-icon ui-icon-triangle-1-n ui-sort-ltr' sort='asc'></SPAN><SPAN class='ui-grid-ico-sort ui-icon-desc ui-state-disabled ui-icon ui-icon-triangle-1-s ui-sort-ltr' sort='desc'></SPAN></SPAN></DIV>");					
							}
							
							
							
							if( cghi.srcColIdx != null && typeof(cghi.srcColIdx) != 'undefined' )
							{
								$colHeader.mousedown(function(e) {
									if ($(e.target).closest("th>span.ui-jqgrid-resize").length != 1) { return; }
									grid.dragStart($(this).attr("srcColIdx"), e, getOffset($(this).attr("srcColIdx")));
									return false;
								}).mouseup(function (e1) {
									if(grid.resizing) {	grid.dragEnd();return false;}
									return true;
								});
							}
						}
						
						$colHeader.attr("colspan", String(colspan));
						if (ts.p.headertitles) {
							$colHeader.attr("title", $colHeader.text());
						}
						
						if( cVisibleColumns === 0) {
							$colHeader.hide();
						}	
						$th.before($colHeader); // insert new column header before the current
						$tr.append(th);         // move the current header in the next row

						// set the coumter of headers which will be moved in the next row
						skip = numberOfColumns - 1;					
					} else {
						
						if (skip <= 0) {						
							tmp[k++] = colModel[i];
							if (o.useColSpanStyle) {
								// expand the header height to two rows
								//alert("attr : " + $th.attr());
								$th.attr("rowspan", (j + 2));
							} else {
								$('<th>', {role: "columnheader"})
									.addClass("ui-state-default ui-th-column-header ui-th-"+ts.p.direction)
									.css({"display": cmi.hidden ? 'none' : '', 'border-top': '0px none'})
									.insertBefore($th);
								$tr.append(th);
							}
						} else {
							// move the header to the next row
							//$th.css({"padding-top": "2px", height: "19px"});
							$tr.append(th);
							skip--;
						}
					}					
				});
				
				$theadInTable = $(ts).children("thead");			
				$theadInTable.prepend($firstHeaderRow);
				$tr.insertAfter($trLabels);
				
				
				$htable.append($theadInTable);	
			}
			
			if (o.useColSpanStyle) {
				// Increase the height of resizing span of visible headers
				$htable.find("span.ui-jqgrid-resize").each(function () {
					var $parent = $(this).parent();
					if ($parent.is(":visible")) {
						this.style.cssText = 'height: ' + $parent.height() + 'px !important; cursor: e-resize;';
					}
				});

				// Set position of the sortable div (the main lable)
				// with the column header text to the middle of the cell.
				// One should not do this for hidden headers.
				$htable.find("div.ui-jqgrid-sortable").each(function () {
					var $ts = $(this), $parent = $ts.parent();
					if ($parent.is(":visible") && $parent.is(":has(span.ui-jqgrid-resize)")) {
						$ts.css('top', ($parent.height() - $ts.outerHeight()) / 2 + 'px');
					}
				});
			}
			
			// Preserve original resizeStop event if any defined
			if ($.isFunction(ts.p.resizeStop)) {
				originalResizeStop = ts.p.resizeStop;
			}
			$firstRow = $theadInTable.find("tr.jqg-first-row-header");
			ts.p.resizeStop = function (nw, idx) {
				$firstRow.find('th').eq(idx).width(nw);
				if ($.isFunction(originalResizeStop)) {
					originalResizeStop.call(ts, nw, idx);
				}
			};
		//	그루핑 헤더 리사이징이 정상적으로 안되던 문제를 우회 처리
			//$(this).closest('div.ui-jqgrid-view').find('>div.ui-jqgrid-hdiv tr.jqg-first-row-header').remove();
		});
	},
	destroyGroupHeader : function(nullHeader)
	{
		if(typeof(nullHeader) == 'undefined') {
			nullHeader = true;
		}
		return this.each(function()
		{
			var $t = this, $tr, i, l, headers, $th, $resizing, grid = $t.grid,
			thead = $("table.ui-jqgrid-htable thead", grid.hDiv), cm = $t.p.colModel, hc;
			if(!grid) return;

			$tr = $("<tr>", {role: "rowheader"}).addClass("ui-jqgrid-labels");
			headers = grid.headers;
			for (i = 0, l = headers.length; i < l; i++) {
				hc = cm[i].hidden ? "none" : "";
				$th = $(headers[i].el)
					.width(headers[i].width)
					.css('display',hc);
				try {
					$th.removeAttr("rowSpan");
				} catch (rs) {
					//IE 6/7
					$th.attr("rowSpan",1);
				}
				$tr.append($th);
				$resizing = $th.children("span.ui-jqgrid-resize");
				if ($resizing.length>0) {// resizable column
					$resizing[0].style.height = "";
				}
				$th.children("div")[0].style.top = "";
			}
			$(thead).children('tr.ui-jqgrid-labels').remove();
			$(thead).prepend($tr);

			if(nullHeader === true) {
				$($t).jqGrid('setGridParam',{ 'groupHeader': null});
			}
		});
	},
	setFrozenColumns : function () {		
		return this.each(function() {
			if ( !this.grid ) {return;}
			var $t = this, cm = $t.p.colModel,i=0, len = cm.length, maxfrozen = -1, frozen= false;
			// TODO treeGrid and grouping  Support
			if($t.p.subGrid == true || $t.p.treeGrid === true || $t.p.cellEdit == true || $t.p.sortable || $t.p.scroll || $t.p.grouping )
			{
				return;
			}
			if($t.p.rownumbers) { i++; }
			if($t.p.multiselect) { i++; }
			
			// get the max index of frozen col
			while(i<len)
			{
				// from left, no breaking frozen
				if(cm[i].frozen === true)
				{
					frozen = true;
					maxfrozen = i;
				} else {
					break;
				}
				i++;
			}
			if( maxfrozen>=0 && frozen) {
				var top = $t.p.caption ? $($t.grid.cDiv).outerHeight() : 0,
				hth = $(".ui-jqgrid-htable","#gview_"+$.jgrid.jqID($t.p.id)).height();
				$t.p.orgEvents = {};
				//headers
				if($t.p.toppager) {
					top = top + $($t.grid.topDiv).outerHeight();
				}
				if($t.p.toolbar[0] == true) {
					if($t.p.toolbar[1] != "bottom") {
						top = top + $($t.grid.uDiv).outerHeight();
					}
				}
				$t.grid.fhDiv = $('<div style="position:absolute;left:0px;top:'+top+'px;height:'+hth+'px;" class="frozen-div ui-state-default ui-jqgrid-hdiv"></div>');
				$t.grid.fbDiv = $('<div style="position:absolute;left:0px;top:'+(parseInt(top,10)+parseInt(hth,10) + 1)+'px;overflow-y:hidden" class="frozen-bdiv ui-jqgrid-bdiv"></div>');
				$("#gview_"+$.jgrid.jqID($t.p.id)).append($t.grid.fhDiv);
				var htbl = $(".ui-jqgrid-htable","#gview_"+$.jgrid.jqID($t.p.id)).clone(true);
				// groupheader support - only if useColSpanstyle is false
				if($t.p.groupHeader) {
					$("tr.jqg-first-row-header, tr.jqg-third-row-header", htbl).each(function(){
						$("th:gt("+maxfrozen+")",this).remove();
					});
					var swapfroz = -1, fdel = -1;
					$("tr.jqg-second-row-header th", htbl).each(function( i ){
						var cs= parseInt($(this).attr("colspan"),10);
						if(cs) {
							swapfroz = swapfroz+cs;
							fdel++;
						}
						if(swapfroz === maxfrozen) {
							return false;
						}
					});
					if(swapfroz !== maxfrozen) {
						fdel = maxfrozen;
					}
					$("tr.jqg-second-row-header", htbl).each(function( i ){
						$("th:gt("+fdel+")",this).remove();
					});
				} else {
					$("tr",htbl).each(function(){
						$("th:gt("+maxfrozen+")",this).remove();
					});
				}
				$(htbl).width(1);
				// resizing stuff
				$($t.grid.fhDiv).append(htbl)
				.mousemove(function (e) {
					if($t.grid.resizing){ $t.grid.dragMove(e);return false; }
				});
				if ($.isFunction($t.p.resizeStop)) {
					$t.p.orgEvents.resizeStop = $t.p.resizeStop;
				}
				$t.p.resizeStop =  function(w, index)
				{
					var rhth = $(".ui-jqgrid-htable",$t.grid.fhDiv);
					$("th:eq("+index+")",rhth).width( w ); 
					var btd = $(".ui-jqgrid-btable",$t.grid.fbDiv);
					$("tr:first td:eq("+index+")",btd).width( w ); 
					
					if ($.isFunction($t.p.orgEvents.resizeStop)) {
						$t.p.orgEvents.resizeStop.call($t, w, index);
					} else {
						$t.p.orgEvents.resizeStop = null;
					}
				};
				// sorting stuff
				if($.isFunction( $t.p.onSortCol)) {
					$t.p.orgEvents.onSortCol = $t.p.onSortCol;
				} else {
					$t.p.orgEvents.onSortCol = null;
				}
				$t.p.onSortCol = function( index,idxcol,so ){
	
					var previousSelectedTh = $("tr.ui-jqgrid-labels:last th:eq("+$t.p.lastsort+")",$t.grid.fhDiv), newSelectedTh = $("tr.ui-jqgrid-labels:last th:eq("+idxcol+")",$t.grid.fhDiv);
	
					$("span.ui-grid-ico-sort",previousSelectedTh).addClass('ui-state-disabled');
					$(previousSelectedTh).attr("aria-selected","false");
					$("span.ui-icon-"+$t.p.sortorder,newSelectedTh).removeClass('ui-state-disabled');
					$(newSelectedTh).attr("aria-selected","true");
					if(!$t.p.viewsortcols[0]) {
						if($t.p.lastsort != idxcol) {
							$("span.s-ico",previousSelectedTh).hide();
							$("span.s-ico",newSelectedTh).show();
						}
					}
					if(	$.isFunction($t.p.orgEvents.onSortCol) ) {
						$t.p.orgEvents.onSortCol.call($t,index,idxcol,so);
					}
				};
				
				// data stuff
				//TODO support for setRowData
				$("#gview_"+$.jgrid.jqID($t.p.id)).append($t.grid.fbDiv);
				jQuery($t.grid.bDiv).scroll(function () {
					jQuery($t.grid.fbDiv).scrollTop(jQuery(this).scrollTop());
				});
				if ($.isFunction($t.p._complete)) {
					$t.p.orgEvents.complete = $t.p._complete;
				} else {
					$t.p.orgEvents.complete = null;
				}
				if($t.p.hoverrows === true) {
					$("#"+$.jgrid.jqID($t.p.id)).unbind('mouseover').unbind('mouseout');
				}
				$t.p._complete = function() {
					$("#"+$.jgrid.jqID($t.p.id)+"_frozen").remove();
					jQuery($t.grid.fbDiv).height( jQuery($t.grid.bDiv).height()-16);
					var btbl = $("#"+$.jgrid.jqID($t.p.id)).clone(true);
					$("tr",btbl).each(function(){
						$("td:gt("+maxfrozen+")",this).remove();
					});
	
					$(btbl).width(1).attr("id",$.jgrid.jqID($t.p.id)+"_frozen");
					$($t.grid.fbDiv).append(btbl);
					if($t.p.hoverrows === true) {
						$("tr.jqgrow", btbl).hover(
							function(){ $(this).addClass("ui-state-hover"); $("#"+$.jgrid.jqID(this.id), "#"+$.jgrid.jqID($t.p.id)).addClass("ui-state-hover") },
							function(){ $(this).removeClass("ui-state-hover"); $("#"+$.jgrid.jqID(this.id), "#"+$.jgrid.jqID($t.p.id)).removeClass("ui-state-hover") }
						);
						$("tr.jqgrow", "#"+$.jgrid.jqID($t.p.id)).hover(
							function(){ $(this).addClass("ui-state-hover"); $("#"+$.jgrid.jqID(this.id), "#"+$.jgrid.jqID($t.p.id)+"_frozen").addClass("ui-state-hover");},
							function(){ $(this).removeClass("ui-state-hover"); $("#"+$.jgrid.jqID(this.id), "#"+$.jgrid.jqID($t.p.id)+"_frozen").removeClass("ui-state-hover"); }
						);
					}
					btbl=null;
					if($.isFunction($t.p.orgEvents.complete)) {
						$t.p.orgEvents.complete.call($t);
					}
				};
				$t.p.frozenColumns = true;
			}
		});
	},
	setRowClass1 : function(selection, onsr)
	{
		return this.each(function(){
			var $t = this, pt;
			
			if(selection === undefined) { return; }
			onsr = onsr === false ? false : true;
			pt=$t.rows.namedItem(selection+"");
			if( onsr )
			{
				$(pt).removeClass("ui-state-highlight");
				$(pt).addClass("ui-state-highlight-nonselect").attr({"aria-selected":"false", "tabindex" : "-1"});
			}	
			else
			{
				$(pt).removeClass("ui-state-highlight-nonselect").attr({"aria-selected":"false", "tabindex" : "-1"});				
			}
			//$t.p.onSelectRow.call($t, -1, false);
			$t.p.selrow = -1;
			$t.p.lastRow = -1;
		});
	},
	clearSelectionIds : function() {
		return this.each(function(){
			var $t = this;
			$($t).jqGrid('setGridParam',{ 'selectionIds':[]});
		});
		
	},
	setScrollTop : function(tLoc) {
		return this.each(function(){
			var $t = this;
			var addTopSize = 100;

			var scrollHeight = $t.grid.bDiv.scrollHeight;
			if( (scrollHeight / 2) > tLoc )
				addTopSize = -addTopSize;
				
			$t.grid.bDiv.scrollTop = $t.grid.bDiv.scrollTop + addTopSize;
		});
	},
	setScrollLeft : function(lLoc) {
		return this.each(function(){
			var $t = this;
			var addLeftSize = 100;

			var scrollWidth = $t.grid.bDiv.scrollWidth;
			if( (scrollWidth / 2) > lLoc )
				addLeftSize = -addLeftSize;
				
			$t.grid.bDiv.scrollLeft = $t.grid.bDiv.scrollLeft + addLeftSize;
		});
	}
	,
	/*
	 * 해당 Row의 선택 해제 처리 (jMultiFilter 사용시 hidden되는 Row에 대한 선택 해제)
	 */
	setNonSelection : function(selection)
	{
		return this.each(function(){
			var $t = this;
			var pt=$t.rows.namedItem(selection+"");
			$t.p.selrow = pt.id;
			var ia = $.inArray($t.p.selrow,$t.p.selarrrow);
			if( ia > -1 )
			{
				$t.p.selarrrow.splice(ia,1);
				$("#jqg_"+$.jgrid.jqID($t.p.id)+"_"+$.jgrid.jqID($t.p.selrow)).attr("checked", false);
				$($t.rows.namedItem($t.p.selrow)).removeClass("ui-state-highlight").attr({"aria-selected":"false", "tabindex" : "-1"});
				$(pt).removeClass("ui-state-highlight-nonselect").attr({"aria-selected":"false", "tabindex" : "0"});
			}
		});
	},
	/*
	 * 해당 Row의 선택 해제 처리 (jMultiFilter 사용시 hidden되는 Row에 대한 선택 해제)
	 */
	setRowHidden : function(rowId, isHidden, isStatus)
	{
		return this.each(function(){
			var $t = this;
			
			if( isStatus )
				$($t).jqGrid("setCell", rowId, "status", "D");
			
			if( isHidden )
			{
				$("#" + rowId, $t).css("display", "none");
	            // 해당 Row의 Checkbox 선택 해제 처리
				$($t).setSelection(rowId, false);
			}
		});
	},
	/*
	 * row 단위로 체크박스 활성화 제어 처리
	 * 최초작성일 : 2014.10.25
	 * 작성자 : 조현기 * 
	 */
	setChkBoxVisible : function(rowId, isVisible) {
		return this.each(function(){
			var $t = this;
			var chkBoxCtl = $("#jqg_".concat($.jgrid.jqID($t.p.id)).concat("_").concat(rowId));
			if( isVisible ) 
				$(chkBoxCtl).show();
			else 
				$(chkBoxCtl).hide();
			
			/* Hide 처리는 되나 Hide만으로는 SetSelection 처리시 강제로 선택되어
			 * isFstChkVisible:true 옵션 추가 (true시 rowId = 1은 선택안됨)
			 */
		});
	},	
	/*
	 * 하나의 레코드만 선택 활성화 되도록 변경 
	 * 최초작성일 : 2011.08.25
	 * 작성자 : 조현기 * 
	 */
	setSelection : function(selection,onsr, isCheck, isStyle) {
		return this.each(function(){
			var $t = this, stat,pt, ner, ia, tpsr, fid;
			var isSelIds = false;
			// multiselect && isMultikeyDisregard 옵션이 활성화 된 경우 각 이벤트에 맞게 Checkbox 선택 및 selection style 적용을 위해 사용 
			if(isCheck === undefined) { isCheck = true; }
			if(isStyle === undefined) { isStyle = true; }			
			
			if(selection === undefined) { return; }
			onsr = onsr === false ? false : true;
			pt=$t.rows.namedItem(selection+"");
			
			for( var selIdx = 0; selIdx < $t.p.selectionIds.length; selIdx++ )
			{
				if( $t.p.selectionIds[selIdx] == pt.id )
				{
					isSelIds = true;
					break;
				}
			}
			if( !isSelIds )
			{
				if(!pt || !pt.className || pt.className.indexOf( 'ui-state-disabled' ) > -1 ) { return; }
				function scrGrid(iR){
					var ch = $($t.grid.bDiv)[0].clientHeight,
					st = $($t.grid.bDiv)[0].scrollTop,
					rpos = $t.rows[iR].offsetTop,
					rh = $t.rows[iR].clientHeight;
					if(rpos+rh >= ch+st) { $($t.grid.bDiv)[0].scrollTop = rpos-(ch+st)+rh+st; }
					else if(rpos < ch+st) {
						if(rpos < st) {
							$($t.grid.bDiv)[0].scrollTop = rpos;
						}
					}
				}
				if($t.p.scrollrows===true) {
					ner = $t.rows.namedItem(selection).rowIndex;
					if(ner >=0 ){
						scrGrid(ner);
					}
				}
				if($t.p.frozenColumns === true ) {
					fid = $t.p.id+"_frozen";
				}
				if( $t.p.lastRow == null )
					$t.p.lastRow = -1;
				
				
				if($t.p.multiselect && $t.p.oneSelect){
					$t.p.selrow = pt.id;
					tpsr = $.inArray($t.p.selrow, $t.p.selarrrow);
					if(tpsr===-1 ){						
						pt.className!=="ui-subgrid" && $(pt).removeClass("ui-state-highlight-nonselect").addClass("ui-state-highlight").attr("aria-selected","true");
						stat=true;
						$("#jqg_"+$.jgrid.jqID($t.p.id)+"_"+$.jgrid.jqID($t.p.selrow)).attr("checked",stat);
						
						if( $t.p.lastRow !== -1 && pt.id !== this.rows[$t.p.lastRow].id )
						{	
							var tmp = $t.rows.namedItem(this.rows[$t.p.lastRow].id+"");
							tmp.className!=="ui-subgrid" && $(tmp).removeClass("ui-state-highlight").attr("aria-selected","false");
						}
						
						if( !$t.p.isFstChkVisible )
							$t.p.selarrrow.push($t.p.selrow);
						else
							if( $t.p.isFstChkVisible && $("#jqg_".concat($.jgrid.jqID($t.p.id)).concat("_").concat($t.p.selrow)).css("display") != "none" )
								$t.p.selarrrow.push($t.p.selrow);
							
					} else {
						pt.className!=="ui-subgrid" && $(pt).removeClass("ui-state-highlight-nonselect").addClass("ui-state-highlight").attr("aria-selected","true");
						
						if( $t.p.lastRow !== -1 && pt.id !== this.rows[$t.p.lastRow].id )
						{
							var tmp = $t.rows.namedItem(this.rows[$t.p.lastRow].id+"");
							tmp.className!=="ui-subgrid" && $(tmp).removeClass("ui-state-highlight").attr("aria-selected","false");
						}
						
						stat=false;
						$("#jqg_"+$.jgrid.jqID($t.p.id)+"_"+$.jgrid.jqID($t.p.selrow)).attr("checked",stat);
						$t.p.selarrrow.splice(tpsr,1);
						tpsr = $t.p.selarrrow[0];
						$t.p.selrow=tpsr===undefined?null:tpsr
					}
					$t.p.lastRow = $t.rows.namedItem(selection).rowIndex;
					$t.p.onSelectRow && onsr && $t.p.onSelectRow.call($t, pt.id, stat)
				} else if(!$t.p.multiselect ) {					
					if(pt.className !== "ui-subgrid") {
						if( $t.p.selrow != pt.id) {
							$($t.rows.namedItem($t.p.selrow)).removeClass("ui-state-highlight").attr({"aria-selected":"false", "tabindex" : "-1"});
							$(pt).removeClass("ui-state-highlight-nonselect").addClass("ui-state-highlight").attr({"aria-selected":"true", "tabindex" : "0"});//.focus();
							if(fid) {
								$("#"+$.jgrid.jqID($t.p.selrow), "#"+$.jgrid.jqID(fid)).removeClass("ui-state-highlight");
								$("#"+$.jgrid.jqID(selection), "#"+$.jgrid.jqID(fid)).removeClass("ui-state-highlight-nonselect").addClass("ui-state-highlight");
							}
							stat = true;
						} else {
							stat = false;
						}
						$t.p.selrow = pt.id;
						if( $t.p.onSelectRow && onsr) { $t.p.onSelectRow.call($t, pt.id, stat); }
					}
				} else {
					$t.setHeadCheckBox( false );
					$t.p.selrow = pt.id;
					ia = $.inArray($t.p.selrow,$t.p.selarrrow);
					// SetSelection 시 선택 스타일 적용
					if( $t.p.isSelectionStyle )
					{
						//unselect selectall checkbox when deselecting a specific row
						if (  ia === -1 ){

							if( isCheck )
							{
								stat = true;
								$("#jqg_"+$.jgrid.jqID($t.p.id)+"_"+$.jgrid.jqID($t.p.selrow))[$t.p.useProp ? 'prop': 'attr']("checked",stat);
								
								if( !$t.p.isFstChkVisible )
									$t.p.selarrrow.push($t.p.selrow);
								else
									if( $t.p.isFstChkVisible && $("#jqg_".concat($.jgrid.jqID($t.p.id)).concat("_").concat($t.p.selrow)).css("display") != "none" )
										$t.p.selarrrow.push($t.p.selrow);								
							}
							else {								
								if( $t.p.selbeforerowid != pt.id) {
									$($t.rows.namedItem($t.p.selbeforerowid)).removeClass("ui-state-highlight").attr({"aria-selected":"false", "tabindex" : "-1"});
									$(pt).removeClass("ui-state-highlight-nonselect").addClass("ui-state-highlight").attr({"aria-selected":"true", "tabindex" : "0"});//.focus();
									
								}
								
							}
							if( isStyle )
							{
								if(pt.className !== "ui-subgrid") { 
									$(pt).removeClass("ui-state-highlight-nonselect").addClass("ui-state-highlight");
									$(pt).attr("aria-selected","true");
								}
								$t.p.selbeforerowid = pt.id;
							}

							
						} else {

								if( isStyle )
								{
									if(pt.className !== "ui-subgrid") { 
										$(pt).removeClass("ui-state-highlight-nonselect ui-state-highlight");//.addClass("ui-state-highlight");
										$(pt).attr("aria-selected","true");
									}
								}
								
								if( isCheck )
								{
									stat = false;
									$("#jqg_"+$.jgrid.jqID($t.p.id)+"_"+$.jgrid.jqID($t.p.selrow))[$t.p.useProp ? 'prop': 'attr']("checked",stat);
									$t.p.selarrrow.splice(ia,1);
									tpsr = $t.p.selarrrow[0];
									$t.p.selrow = (tpsr === undefined) ? null : tpsr;
								}
								else
								{
									if( $t.p.selbeforerowid != pt.id) {
										$($t.rows.namedItem($t.p.selbeforerowid)).removeClass("ui-state-highlight").attr({"aria-selected":"false", "tabindex" : "-1"});
										$(pt).removeClass("ui-state-highlight-nonselect").addClass("ui-state-highlight").attr({"aria-selected":"true", "tabindex" : "0"});//.focus();
										
									}							
								}

								$t.p.selbeforerowid = pt.id;
							
						}						
						if(fid) {
							if(ia === -1) {
								if( isCheck )
									$("#"+$.jgrid.jqID(selection), "#"+$.jgrid.jqID(fid)).removeClass("ui-state-highlight-nonselect").addClass("ui-state-highlight");
							} else {
								$("#"+$.jgrid.jqID(selection), "#"+$.jgrid.jqID(fid)).removeClass("ui-state-highlight");
							}
							$("#jqg_"+$.jgrid.jqID($t.p.id)+"_"+$.jgrid.jqID(selection), "#"+$.jgrid.jqID(fid))[$t.p.useProp ? 'prop': 'attr']("checked",stat);
						}
						if( $t.p.onSelectRow && onsr) {							
							$t.p.onSelectRow.call($t, pt.id , stat); 
						}
					}
					else
					{
						//unselect selectall checkbox when deselecting a specific row
						if (  ia === -1 ){
							stat = true;
							$("#jqg_"+$.jgrid.jqID($t.p.id)+"_"+$.jgrid.jqID($t.p.selrow))[$t.p.useProp ? 'prop': 'attr']("checked",stat);
							
							if( !$t.p.isFstChkVisible )
								$t.p.selarrrow.push($t.p.selrow);
							else
								if( $t.p.isFstChkVisible && $("#jqg_".concat($.jgrid.jqID($t.p.id)).concat("_").concat($t.p.selrow)).css("display") != "none" )
									$t.p.selarrrow.push($t.p.selrow);
						} else {
							stat = false;
							$("#jqg_"+$.jgrid.jqID($t.p.id)+"_"+$.jgrid.jqID($t.p.selrow))[$t.p.useProp ? 'prop': 'attr']("checked",stat);
							$t.p.selarrrow.splice(ia,1);
							tpsr = $t.p.selarrrow[0];
							$t.p.selrow = (tpsr === undefined) ? null : tpsr;
						}
						if(fid) {
							$("#jqg_"+$.jgrid.jqID($t.p.id)+"_"+$.jgrid.jqID(selection), "#"+$.jgrid.jqID(fid))[$t.p.useProp ? 'prop': 'attr']("checked",stat);
						}
						if( $t.p.onSelectRow && onsr) { $t.p.onSelectRow.call($t, pt.id , stat); }
					}
					

					$t.p.lastRow = $t.rows.namedItem(selection).rowIndex;
				}
			}
		});
	},	
	editCell : function (iRow,iCol, ed){
		return this.each(function (){
			var $t = this, nm, tmp,cc;
			if(!(! $t.grid|| $t.p.cellEdit!==true))
			{
				iCol = parseInt(iCol,10);
				//alert("getCellEditable(iRow, iCol) : " + getCellEditable(iRow, iCol));
				
				/*
				if( !getCellEditable($t.rows[iRow].id, iCol) )
				{
					alert("test");
					$($t).jqGrid("restoreCell",iRow,iCol);
					//$($t).jqGrid("exitCell", iRow, iCol);
				}
				*/

				var rowcnt = parseInt($($t).getGridParam("records"));
				if( rowcnt >= parseInt(iRow) )
				{
					try{
						$t.p.selrow = $t.rows[iRow].id;
						$t.p.knv||$($t).jqGrid("GridNav");
					}
					catch(error)
					{
						return;
					}
				}
				else
				{
					return;
				}
				
				// select the row that can be used for other methods
				$t.p.selrow = $t.rows[iRow].id;
				if (!$t.p.knv) {$($t).jqGrid("GridNav");}
				// check to see if we have already edited cell
				if ($t.p.savedRow.length>0) {
					// prevent second click on that field and enable selects
					if (ed===true ) {
						if(iRow == $t.p.iRow && iCol == $t.p.iCol){
							return;
						}
					}
					// save the cell					
					$($t).jqGrid("saveCell",$t.p.savedRow[0].id,$t.p.savedRow[0].ic);
				} else {
					window.setTimeout(function () { $("#"+$t.p.knv).attr("tabindex","-1").focus();},0);
				}
				nm = $t.p.colModel[iCol].name;
				if (nm=='subgrid' || nm=='cb' || nm=='rn') {return;}
				cc = $("td:eq("+iCol+")",$t.rows[iRow]);
				if ($t.p.colModel[iCol].editable===true && ed===true && !cc.hasClass("not-editable-cell")) {
					if(parseInt($t.p.iCol,10)>=0  && parseInt($t.p.iRow,10)>=0) {
						$("td:eq("+$t.p.iCol+")",$t.rows[$t.p.iRow]).removeClass("edit-cell ui-state-highlight");
						$($t.rows[$t.p.iRow]).removeClass("selected-row ui-state-hover");
					}
					$(cc).removeClass("ui-state-highlight-nonselect").addClass("edit-cell ui-state-highlight");
					$($t.rows[iRow]).addClass("selected-row ui-state-hover");
					try {
						tmp =  $.unformat(cc,{rowId: $t.rows[iRow].id, colModel:$t.p.colModel[iCol]},iCol);
					} catch (_) {
						tmp = $(cc).html();
					}
					if($t.p.autoencode) { tmp = $.jgrid.htmlDecode(tmp); }
					if (!$t.p.colModel[iCol].edittype) {$t.p.colModel[iCol].edittype = "text";}
					$t.p.savedRow.push({id:iRow,ic:iCol,name:nm,v:tmp});
					if($.isFunction($t.p.formatCell)) {
						var tmp2 = $t.p.formatCell.call($t, $t.rows[iRow].id,nm,tmp,iRow,iCol);
						if(tmp2 !== undefined ) {tmp = tmp2;}
					}
					var opt = $.extend({}, $t.p.colModel[iCol].editoptions || {} ,{id:iRow+"_"+nm,name:nm});
					var elc = $.jgrid.createEl($t.p.colModel[iCol].edittype,opt,tmp,true,$.extend({},$.jgrid.ajaxOptions,$t.p.ajaxSelectOptions || {}));
					if ($.isFunction($t.p.beforeEditCell)) {
						$t.p.beforeEditCell.call($t, $t.rows[iRow].id,nm,tmp,iRow,iCol);
					}
					$(cc).html("").append(elc).attr("tabindex","0");
					window.setTimeout(function () { $(elc).focus();},0);
					$("input, select, textarea",cc).bind("keydown",function(e) {
						if( $t.p.isCellRwChk )
						{
							// $t.p.isCellRwChk 를 사용하는 경우 사용자 Page에 getCellEditable 구현되어야 함
							if( !getCellEditable($t.rows[iRow].id, iCol, true) )
							{
								$($t).jqGrid("restoreCell",iRow,iCol);
							}
						}
						/* Ctrl+V 처리 */
						if (e.ctrlKey==true && (e.which == '118' || e.which == '86')) {
							if( $t.p.rownumbers ) iCol++;
							if($.browser.webkit || $.browser.safari )
							{
								$($t).jqGrid("restoreCell", iRow, iCol);
								ggridId = $t.p.id;
								giRow = iRow;
								giCol = iCol;
								gColLength = $t.grid.cols.length;
								// 크롬의 경우 Ctrl+V 사용시 Body에 paste 이벤트 설정
								document.body.addEventListener("paste", bodyEvent, false); 
								
								// 상단의 body의 paste 이벤트 Remove 처리
								setTimeout(function(){
									document.body.removeEventListener("paste", bodyEvent, false);
								},100);								
							}
							else if( $.browser.msie || $.browser.mozilla )
							{
								$($t).jqGrid("restoreCell", iRow, iCol);
								// grid.paste.js 에 선언
								// 단, 보고통계 수치입력의 경우 ScmNclInputPopup.jsp 에 선언됨
								Paste($t.p.id, iRow, iCol, $t.grid.cols.length, e); 
							}
						}
						
						if (e.keyCode === 27 || e.keyCode == undefined ) {
							if($("input.hasDatepicker",cc).length >0) {
								if( $(".ui-datepicker").is(":hidden") )  { $($t).jqGrid("restoreCell",iRow,iCol); }
								else { $("input.hasDatepicker",cc).datepicker('hide'); }
							} else {
								$($t).jqGrid("restoreCell",iRow,iCol);
							}
						} //ESC
						
						// MultiHeader Enter
						//if (e.keyCode === 13) {$($t).jqGrid("saveCell",iRow,iCol);}					
						/* EnterKey Event시 저장기능 주석처리 l.keyCode===13&&b(c).jqGrid("saveCell",d,e); */
						if (e.keyCode === 13) {
							if( !$t.p.isEnterCellFix )
								$($t).jqGrid("nextCell",iRow,iCol);
							else
								$($t).jqGrid("saveCell",iRow,iCol);
						}
						/* left, up, right, down arrow 처리 */
						if ( e.ctrlKey==true && e.keyCode === 37) {
							$($t).jqGrid("prevCell",iRow,iCol);
						}
						if ( e.ctrlKey==true && e.keyCode === 38) {
							$($t).jqGrid("upArrowCell",iRow,iCol);
						}
						if ( e.ctrlKey==true && e.keyCode === 39) {
							$($t).jqGrid("nextCell",iRow,iCol);
						}
						if ( e.ctrlKey==true && e.keyCode === 40) {
							$($t).jqGrid("downArrowCell", iRow, iCol);
						}
	
						//tab 키 
						if (e.keyCode == 9)  {
							if(!$t.grid.hDiv.loading ) {
								if (e.shiftKey) {$($t).jqGrid("prevCell",iRow,iCol);} //Shift TAb
								else {$($t).jqGrid("nextCell",iRow,iCol);} //Tab
							} else {
								return false;
							}
						}
						// 특수문자 입력 제어 처리
						if( !exclusionSpecCharValide($t, e) )
							$($t).jqGrid("restoreCell",iRow,iCol);
						
						e.stopPropagation();
					}).blur(function() {
						$($t).jqGrid("saveCell", iRow, iCol);
					});
					
					/*
                	 * ie9인 경우 Grid내의 다른 Cell 클릭시 이전 EditCell에서 blur event 미발생에 대한 오류로 인한 Grid 오류 보완
                	
                	if( $.browser.msie && $.browser.version > 8 ) {
                		
                		$("input, textarea",cc).mouseleave(function() {
                			$(this).trigger("blur");
                		});                		
                		
                		$("select",cc).change(function() {
                			$(this).trigger("blur");
                		});
                	}*/
					
					if($("input.hasDatepicker",cc).length > 0)
					{
						$("input, select, textarea",cc).unbind("blur").change(function(e) {
							$($t).jqGrid("saveCell", $t.p.savedRow[0].id, $t.p.savedRow[0].ic)
						});
					}
					
					if( $t.rows[iRow].id == -1 )
					{
						var aevent = $.Event("keydown");
						aevent.keyCode = 27;
						$("input, select, textarea",cc).focus(function() {							
							$(this).trigger(aevent);
						});
					}
					
					if ($.isFunction($t.p.afterEditCell)) {						
						$t.p.afterEditCell.call($t, $t.rows[iRow].id,nm,tmp,iRow,iCol);
					}
				} else {
					if (parseInt($t.p.iCol,10)>=0  && parseInt($t.p.iRow,10)>=0) {
						$("td:eq("+$t.p.iCol+")",$t.rows[$t.p.iRow]).removeClass("edit-cell ui-state-highlight");
						$($t.rows[$t.p.iRow]).removeClass("selected-row ui-state-hover");
					}
					cc.removeClass("ui-state-highlight-nonselect").addClass("edit-cell ui-state-highlight");
					$($t.rows[iRow]).addClass("selected-row ui-state-hover"); 
					if ($.isFunction($t.p.onSelectCell)) {
						tmp = cc.html().replace(/\&#160\;/ig,'');
						$t.p.onSelectCell.call($t, $t.rows[iRow].id,nm,tmp,iRow,iCol);
					}
				}
				$t.p.iCol = iCol; $t.p.iRow = iRow;
			}
			
			
		});
		
		/*
		 * 특수문자 입력제어 처리
		 */
		function exclusionSpecCharValide($t, e)
		{
			if( $t.p.exclusionSpecCharList != null )
			{
				for( var i = 0; i < $t.p.exclusionSpecCharList.length; i++ )
				{
					if( e.shiftKey && e.keyCode == $t.p.exclusionSpecCharList[i][0] )
					{
						alert("특수문자 ' " + $t.p.exclusionSpecCharList[i][2] + " ' 는 입력할 수 없습니다.");
						return false;
					}
				}
			}
			return true;
		}
		
		function bodyEvent(e) {
			// grid.paste.js 에 선언
			// 단, 보고통계 수치입력의 경우 ScmNclInputPopup.jsp 에 선언됨
			Paste(ggridId, giRow, giCol, gColLength, e);
		}
	},
	saveCell : function (iRow, iCol){
		return this.each(function(){
			var $t= this, fr;
			if (!$t.grid || $t.p.cellEdit !== true) {return;}
			if ( $t.p.savedRow.length >= 1) {fr = 0;} else {fr=null;} 
			if(fr !== null) {
				var cc = $("td:eq("+iCol+")",$t.rows[iRow]),v,v2,
				cm = $t.p.colModel[iCol], nm = cm.name, nmjq = $.jgrid.jqID(nm) ;
				switch (cm.edittype) {
					case "select":
						if(!cm.editoptions.multiple) {
							v = $("#"+iRow+"_"+nmjq+">option:selected",$t.rows[iRow]).val();
							v2 = $("#"+iRow+"_"+nmjq+">option:selected",$t.rows[iRow]).text();
						} else {
							var sel = $("#"+iRow+"_"+nmjq,$t.rows[iRow]), selectedText = [];
							v = $(sel).val();
							if(v) { v.join(",");} else { v=""; }
							$("option:selected",sel).each(
								function(i,selected){
									selectedText[i] = $(selected).text();
								}
							);
							v2 = selectedText.join(",");
						}
						if(cm.formatter) { v2 = v; }
						break;
					case "checkbox":
						var cbv  = ["Yes","No"];
						if(cm.editoptions){
							cbv = cm.editoptions.value.split(":");
						}
						v = $("#"+iRow+"_"+nmjq,$t.rows[iRow]).attr("checked") ? cbv[0] : cbv[1];
						v2=v;
						break;
					case "password":
					case "text":
					case "textarea":
					case "button" :
						v = $("#"+iRow+"_"+nmjq,$t.rows[iRow]).val();
						v2=v;
						break;
					case 'custom' :
						try {
							if(cm.editoptions && $.isFunction(cm.editoptions.custom_value)) {
								v = cm.editoptions.custom_value.call($t, $(".customelement",cc),'get');
								if (v===undefined) { throw "e2";} else { v2=v; }
							} else { throw "e1"; }
						} catch (e) {
							if (e=="e1") { $.jgrid.info_dialog(jQuery.jgrid.errors.errcap,"function 'custom_value' "+$.jgrid.edit.msg.nodefined,jQuery.jgrid.edit.bClose); }
							if (e=="e2") { $.jgrid.info_dialog(jQuery.jgrid.errors.errcap,"function 'custom_value' "+$.jgrid.edit.msg.novalue,jQuery.jgrid.edit.bClose); }
							else {$.jgrid.info_dialog(jQuery.jgrid.errors.errcap,e.message,jQuery.jgrid.edit.bClose); }
						}
						break;
				}
				// The common approach is if nothing changed do not do anything
				if (v2 != $t.p.savedRow[fr].v){
					if ($.isFunction($t.p.beforeSaveCell)) {
						var vv = $t.p.beforeSaveCell.call($t, $t.rows[iRow].id,nm, v, iRow,iCol);
						if (vv) {v = vv; v2=vv;}
					}
					var cv = $.jgrid.checkValues(v,iCol,$t);
					//alert("cv : " + cv[0]);
					if(cv[0]===true)
						$($t).jqGrid("exitCell", iRow, iCol, v2);
					else
					{
						try {
							window.setTimeout(function(){alert(v+" "+cv[1])},100);
//	window.setTimeout(function(){$.jgrid.info_dialog($.jgrid.errors.errcap,v+" "+cv[1],$.jgrid.edit.bClose)},100);
						}catch(u){}
						$($t).jqGrid("restoreCell", iRow, iCol);
					}
					/*
					if(cv[0] === true) {
						var addpost = {};
						if ($.isFunction($t.p.beforeSubmitCell)) {
							addpost = $t.p.beforeSubmitCell.call($t, $t.rows[iRow].id,nm, v, iRow,iCol);
							if (!addpost) {addpost={};}
						}
						//if( $("input.hasDatepicker",cc).length >0) { $("input.hasDatepicker",cc).datepicker('hide'); }
						if( $("input.hasDatepicker",cc).length >0) { $("input.hasDatepicker",cc).datepicker('hide'); }
						if ($t.p.cellsubmit == 'remote') {
							if ($t.p.cellurl) {
								var postdata = {};
								if($t.p.autoencode) { v = $.jgrid.htmlEncode(v); }
								postdata[nm] = v;
								var idname,oper, opers;
								opers = $t.p.prmNames;
								idname = opers.id;
								oper = opers.oper;
								postdata[idname] = $t.rows[iRow].id;
								postdata[oper] = opers.editoper;
								postdata = $.extend(addpost,postdata);
								$("#lui_"+$t.p.id).show();
								$t.grid.hDiv.loading = true;
								$.ajax( $.extend( {
									url: $t.p.cellurl,
									data :$.isFunction($t.p.serializeCellData) ? $t.p.serializeCellData.call($t, postdata) : postdata,
									type: "POST",
									complete: function (result, stat) {
										$("#lui_"+$t.p.id).hide();
										$t.grid.hDiv.loading = false;
										if (stat == 'success') {
											if ($.isFunction($t.p.afterSubmitCell)) {
												var ret = $t.p.afterSubmitCell.call($t, result,postdata.id,nm,v,iRow,iCol);
												if(ret[0] === true) {
													$(cc).empty();
													$($t).jqGrid("setCell",$t.rows[iRow].id, iCol, v2, false, false, true);
													$(cc).addClass("dirty-cell");
													$($t.rows[iRow]).addClass("edited");
													if ($.isFunction($t.p.afterSaveCell)) {
														$t.p.afterSaveCell.call($t, $t.rows[iRow].id,nm, v, iRow,iCol);
													}
													$t.p.savedRow.splice(0,1);
												} else {
													$.jgrid.info_dialog($.jgrid.errors.errcap,ret[1],$.jgrid.edit.bClose);
													$($t).jqGrid("restoreCell",iRow,iCol);
												}
											} else {
												$(cc).empty();
												$($t).jqGrid("setCell",$t.rows[iRow].id, iCol, v2, false, false, true);
												$(cc).addClass("dirty-cell");
												$($t.rows[iRow]).addClass("edited");
												if ($.isFunction($t.p.afterSaveCell)) {
													$t.p.afterSaveCell.call($t, $t.rows[iRow].id,nm, v, iRow,iCol);
												}
												$t.p.savedRow.splice(0,1);
											}
										}
									},
									error:function(res,stat) {
										$("#lui_"+$t.p.id).hide();
										$t.grid.hDiv.loading = false;
										if ($.isFunction($t.p.errorCell)) {
											$t.p.errorCell.call($t, res,stat);
											$($t).jqGrid("restoreCell",iRow,iCol);
										} else {
											$.jgrid.info_dialog($.jgrid.errors.errcap,res.status+" : "+res.statusText+"<br/>"+stat,$.jgrid.edit.bClose);
											$($t).jqGrid("restoreCell",iRow,iCol);
										}
									}
								}, $.jgrid.ajaxOptions, $t.p.ajaxCellOptions || {}));
							} else {
								try {
									
									$.jgrid.info_dialog($.jgrid.errors.errcap,$.jgrid.errors.nourl,$.jgrid.edit.bClose);
									$($t).jqGrid("restoreCell",iRow,iCol);
								} catch (e) {}
							}
						}
						if ($t.p.cellsubmit == 'clientArray') {
							$(cc).empty();
							$($t).jqGrid("setCell",$t.rows[iRow].id,iCol, v2, false, false, true);
							$(cc).addClass("dirty-cell");
							$($t.rows[iRow]).addClass("edited");
							if ($.isFunction($t.p.afterSaveCell)) {
								$t.p.afterSaveCell.call($t, $t.rows[iRow].id,nm, v, iRow,iCol);
							}
							$t.p.savedRow.splice(0,1);
						}
					} else {
						try {
							window.setTimeout(function(){$.jgrid.info_dialog($.jgrid.errors.errcap,v+" "+cv[1],$.jgrid.edit.bClose);},100);
							$($t).jqGrid("restoreCell",iRow,iCol);
						} catch (e) {}
					}
							*/
				} else {
					$($t).jqGrid("restoreCell",iRow,iCol);
				}
		
			}
			if ($.browser.opera) {
				$("#"+$t.p.knv).attr("tabindex","-1").focus();
			} else {
				window.setTimeout(function () { $("#"+$t.p.knv).attr("tabindex","-1").focus();},0);
			}
		});
	},
	exitCell:function(iRow, iCol, v2) {	
		return this.each(function(){
			var $t= this, fr;
			//if (!$t.grid || $t.p.cellEdit !== true ) {return;}
			if (!$t.grid ) {return;}
			if ( $t.p.savedRow.length >= 1) {fr = 0;} else {fr=null;}
			if(fr !== null) {
				/*
				var cc = $("td:eq("+iCol+")",$t.rows[iRow]);				
				// datepicker fix
				if($.isFunction($.fn.datepicker)) {
					try {
						$("input.hasDatepicker",cc).datepicker('hide');
					} catch (e) {}
				}
				$(cc).empty().attr("tabindex","-1");
				*/
				$($t).jqGrid("setCell",$t.rows[iRow].id, iCol, v2, false, false, true);
				if ($.isFunction($t.p.afterRestoreCell)) {
					$t.p.afterRestoreCell.call($t, $t.rows[iRow].id, v2, iRow, iCol);
				}	
				$t.p.savedRow.splice(0,1);				
			}
			window.setTimeout(function () { $("#"+$t.p.knv).attr("tabindex","-1").focus();},0);			
		});
	},	
	restoreCell : function(iRow, iCol) {
		return this.each(function(){
			var $t= this, fr;
			if (!$t.grid || $t.p.cellEdit !== true ) {return;}
			if ( $t.p.savedRow.length >= 1) {fr = 0;} else {fr=null;}
			if(fr !== null) {
				var cc = $("td:eq("+iCol+")",$t.rows[iRow]);
				// datepicker fix
				if($.isFunction($.fn.datepicker)) {
					try {
						$("input.hasDatepicker",cc).datepicker('hide');
					} catch (e) {}
				}
				$(cc).empty().attr("tabindex","-1");
				$($t).jqGrid("setCell",$t.rows[iRow].id, iCol, $t.p.savedRow[fr].v, false, false, true);
				if ($.isFunction($t.p.afterRestoreCell)) {
					$t.p.afterRestoreCell.call($t, $t.rows[iRow].id, $t.p.savedRow[fr].v, iRow, iCol);
				}				
				$t.p.savedRow.splice(0,1);
			}			
			window.setTimeout(function () { $("#"+$t.p.knv).attr("tabindex","-1").focus();},0);
		});
	},
	/* EditMode시 DownArrowKey 제어 */
	downArrowCell : function (iRow,iCol) {
		return this.each(function (){
			var $t = this, nRow=false;
			var rownum = parseInt($($t).getGridParam("rowNum"));	
			var currpage = parseInt($($t).getGridParam("page"));
			var records = parseInt($($t).getGridParam("records"));
			var totpage = parseInt((records / rownum) + 1);
			var currpagerecord = 0;
			
			// 마지막 페이지
			if( currpage == totpage )
			{
				currpagerecord = records - (currpage * rownum);
			}
			else
			{
				currpagerecord = rownum;
			}
			if( iRow == currpagerecord )
			{
				return;
			}
			else
			{
				/*if (!$t.grid || $t.p.cellEdit !== true) {return;} */
				if (!$t.grid ) {return;}
				$($t).jqGrid("editCell",iRow+1,iCol,true);
			}		
		});
	},
	/* EditMode시 UpArrowKey 제어 */
	upArrowCell : function (iRow,iCol) {
		return this.each(function (){
			var $t = this, nRow=false;
			if( iRow == 1 )
			{
				return;
			}
			else
			{
				/*if (!$t.grid || $t.p.cellEdit !== true) {return;} */
				if (!$t.grid ) {return;}
				$($t).jqGrid("editCell",iRow-1,iCol,true);
			}		
		});
	},
	nextCell : function (iRow,iCol) {
		return this.each(function (){
			var $t = this, nCol=false;
			if (!$t.grid || $t.p.cellEdit !== true) {return;}
			// try to find next editable cell
			for (var i=iCol+1; i<$t.p.colModel.length; i++) {
				if ( $t.p.colModel[i].editable ===true) {
					nCol = i; break;
				}
			}
			if(nCol !== false) {
				$($t).jqGrid("editCell",iRow,nCol,true);
			} else {
				var rownum = parseInt($(this).getGridParam("rowNum"));			
				var currpage = parseInt($(this).getGridParam("page"));				
				var records = parseInt($(this).getGridParam("records"));
				 
				rownum = (rownum < 1 ? 10000 : rownum);
				var totpage = parseInt((records / rownum) + 1);				
				var currpagerecord = 0;
				currpage = (currpage < 1 ? 1 : currpage);
				totpage = (totpage < 1 ? 1 : totpage);
				
				/* 마지막 페이지*/
				if( currpage == totpage )
				{
					currpagerecord = records - (currpage * rownum);
				}
				else
				{
					currpagerecord = rownum;
				}
				currpagerecord = ( currpagerecord < 0 ? (currpage * rownum) + currpagerecord : currpagerecord);
				
				if( iRow + 1 <= currpagerecord )
				{	
					if (!this.grid ) {return;}
					
					for(var c=iCol+1;c<this.p.colModel.length;c++) {
						if(this.p.colModel[c].editable===true && this.p.colModel[c].hidden===false){
							if( $t.p.isCellRwChk )
							{
								if( getCellEditable($t.rows[iRow+1].id, c) )
								{
									nCol=c;
									break;
								}
							}
							else
								nCol = c;
						}
					}
					if(nCol !==false)
					{
						$(this).jqGrid("editCell",iRow+1, nCol, true);
					}
					else
					{
						if( $t.p.isCellRwChk )
						{
							$(this).jqGrid("editCell",iRow+1, ($t.p.tableRowSpanTgt.length),true);
						}
						else
						{
							for(var c=0;c<this.p.colModel.length;c++) {
								if(this.p.colModel[c].editable===true && this.p.colModel[c].hidden===false){
									nCol = c;
									break;
								}
							}
							$(this).jqGrid("editCell",iRow+1, nCol, true);
						}
					}
				}
				else
				{
					$($t).jqGrid("saveCell",iRow,iCol);
				}
			}			
		});
	},
	prevCell : function (iRow,iCol) {
		return this.each(function (){
			var $t = this, nCol=false;
			if (!$t.grid || $t.p.cellEdit !== true) {return;}
			// try to find next editable cell
			for (var i=iCol-1; i>=0; i--) {
				if ( $t.p.colModel[i].editable ===true) {
					nCol = i; break;
				}
			}
			if(nCol !== false) {
				$($t).jqGrid("editCell",iRow,nCol,true);
			} else {
				if ($t.p.savedRow.length >0) {
					$($t).jqGrid("saveCell",iRow,iCol);
				}
			}
		});
	},
	GridNav : function() {
		return this.each(function () {
			var  $t = this;
			if (!$t.grid || $t.p.cellEdit !== true ) {return;}
			// trick to process keydown on non input elements
			$t.p.knv = $t.p.id + "_kn";
			var selection = $("<span style='width:0px;height:0px;background-color:black;' tabindex='0'><span tabindex='-1' style='width:0px;height:0px;background-color:grey' id='"+$t.p.knv+"'></span></span>"),
			i, kdir;
			$(selection).insertBefore($t.grid.cDiv);
			$("#"+$t.p.knv)
			.focus()
			.keydown(function (e){
				kdir = e.keyCode;
				
				/*
				// Ctrl+V 처리
				if (e.ctrlKey==true && (e.which == '118' || e.which == '86')) {
					alert("GridNav");
					Paste($t.p.id, $t.p.iRow, $t.p.iCol, $t.grid.cols.length); 
			    }
				*/
				
				if($t.p.direction == "rtl") {
					if(kdir==37) { kdir = 39;}
					else if (kdir==39) { kdir = 37; }
				}
				switch (kdir) {
					case 38:
						if ($t.p.iRow-1 >0 ) {
							scrollGrid($t.p.iRow-1,$t.p.iCol,'vu');
							$($t).jqGrid("editCell",$t.p.iRow-1,$t.p.iCol,false);
						}
					break;
					case 40 :
						if ($t.p.iRow+1 <=  $t.rows.length-1) {
							scrollGrid($t.p.iRow+1,$t.p.iCol,'vd');
							$($t).jqGrid("editCell",$t.p.iRow+1,$t.p.iCol,false);
						}
					break;
					case 37 :
						if ($t.p.iCol -1 >=  0) {
							i = findNextVisible($t.p.iCol-1,'lft');
							scrollGrid($t.p.iRow, i,'h');
							$($t).jqGrid("editCell",$t.p.iRow, i,false);
						}
					break;
					case 39 :
						if ($t.p.iCol +1 <=  $t.p.colModel.length-1) {
							i = findNextVisible($t.p.iCol+1,'rgt');
							scrollGrid($t.p.iRow,i,'h');
							$($t).jqGrid("editCell",$t.p.iRow,i,false);
						}
					break;
					case 13:
						if (parseInt($t.p.iCol,10)>=0 && parseInt($t.p.iRow,10)>=0) {
							$($t).jqGrid("editCell",$t.p.iRow,$t.p.iCol,true);
						}
					break;
				}
				return false;
			});
			function scrollGrid(iR, iC, tp){
				if (tp.substr(0,1)=='v') {
					var ch = $($t.grid.bDiv)[0].clientHeight,
					st = $($t.grid.bDiv)[0].scrollTop,
					nROT = $t.rows[iR].offsetTop+$t.rows[iR].clientHeight,
					pROT = $t.rows[iR].offsetTop;
					if(tp == 'vd') {
						if(nROT >= ch) {
							$($t.grid.bDiv)[0].scrollTop = $($t.grid.bDiv)[0].scrollTop + $t.rows[iR].clientHeight;
						}
					}
					if(tp == 'vu'){
						if (pROT < st ) {
							$($t.grid.bDiv)[0].scrollTop = $($t.grid.bDiv)[0].scrollTop - $t.rows[iR].clientHeight;
						}
					}
				}
				if(tp=='h') {
					var cw = $($t.grid.bDiv)[0].clientWidth,
					sl = $($t.grid.bDiv)[0].scrollLeft,
					nCOL = $t.rows[iR].cells[iC].offsetLeft+$t.rows[iR].cells[iC].clientWidth,
					pCOL = $t.rows[iR].cells[iC].offsetLeft;
					if(nCOL >= cw+parseInt(sl,10)) {
						$($t.grid.bDiv)[0].scrollLeft = $($t.grid.bDiv)[0].scrollLeft + $t.rows[iR].cells[iC].clientWidth;
					} else if (pCOL < sl) {
						$($t.grid.bDiv)[0].scrollLeft = $($t.grid.bDiv)[0].scrollLeft - $t.rows[iR].cells[iC].clientWidth;
					}
				}
			}
			function findNextVisible(iC,act){
				var ind, i;
				if(act == 'lft') {
					ind = iC+1;
					for (i=iC;i>=0;i--){
						if ($t.p.colModel[i].hidden !== true) {
							ind = i;
							break;
						}
					}
				}
				if(act == 'rgt') {
					ind = iC-1;
					for (i=iC; i<$t.p.colModel.length;i++){
						if ($t.p.colModel[i].hidden !== true) {
							ind = i;
							break;
						}						
					}
				}
				return ind;
			}
		});
	},
	setCell : function(rowid,colname,nData,cssp,attrp, forceupd) {
		return this.each(function(){
			var $t = this, pos =-1,v, title;
			if(!$t.grid) {return;}
			if(isNaN(colname)) {
				$($t.p.colModel).each(function(i){
					if (this.name == colname) {
						pos = i;return false;
					}
				});
			} else {pos = parseInt(colname,10);}
			if(pos>=0) {
				var ind = $t.rows.namedItem(rowid);
				if (ind){
					var tcell = $("td:eq("+pos+")",ind);
					v = $t.formatter(rowid, nData, pos,ind,'edit');
					title = $t.p.colModel[pos].title ? {"title":$.jgrid.stripHtml(v)} : {};
					if($t.p.treeGrid && $(".tree-wrap",$(tcell)).length>0) {
						$("span",$(tcell)).html(v).attr(title);
					} else {
						$(tcell).html(v).attr(title);
					}
					if($t.p.datatype == "local") {
						var cm = $t.p.colModel[pos], index;
						nData = cm.formatter && typeof(cm.formatter) === 'string' && cm.formatter == 'date' ? $.unformat.date(nData,cm) : nData;
						index = $t.p._index[rowid];
						if(typeof index  != "undefined") {
							$t.p.data[index][cm.name] = nData;
						}
					}
					if(typeof cssp === 'string'){
						$(tcell).addClass(cssp);
					} else if(cssp) {
						$(tcell).css(cssp);
					}
					if(typeof attrp === 'object') {$(tcell).attr(attrp);}
				}
			}
		});
	},
	/* Header의 CheckBox 강제 제어 처리 */
	headerChkBoxClick : function(isChecked) {
		return this.each(function(){
			var t = this;
			$("#cb_" + t.p.id).attr("checked", isChecked).trigger("click").attr("checked", isChecked);
		});
	},
	/* 사용자로 부터 더블클릭 강제 호출 처리 */
	dblClickRow : function(rowIdx) {
		return this.each(function(){
			var t = this, rowid;
			var gridRows = $('tbody tr', t);
		    gridRows = $(gridRows).filter(':visible:not(.jqgfirstrow)');
		    rowid = $(gridRows[rowIdx]).attr("id");
		    if( rowid == undefined )
		    	rowid = -100;
		    
		    t.p.lastRow = -1;
		    
			t.p.ondblClickRow.call(t,rowid,0,1, null);
			// 1건만 선택되도록 임시 변경
			t.p.oneSelect = true;
			// 선택정보 초기화
			t.p.selarrrow = [];
			$(t).setSelection(rowid);
			//$(t).jqGrid("setSelection",rowid,true);
			// n건 선택되도록 임시 변경
			t.p.oneSelect = false;
		});
	}
});

})(jQuery);

