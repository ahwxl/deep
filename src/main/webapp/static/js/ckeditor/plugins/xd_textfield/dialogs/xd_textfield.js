/**
 * 讯点智能表单设计器   textfield
 * @author tony 2012-08-07
 */
CKEDITOR.dialog.add( 'xd_textfield', function( editor )
{
	var autoAttributes =
	{
		value : 1,
		size : 1,
		maxLength : 1
	};

	var acceptedTypes =
	{
		text : 1,
		password : 1
	};
	
	var elements = [
				{
					type : 'hbox',
					widths : [ '50%', '50%' ],
					children :
					[
						{
							id : 'txtName',
							type : 'text',
							label : '控件名称'
						},
						{
							id : 'txtAlign',
							type : 'select',
							label : '对齐方式',
							'default' : 'text',
							accessKey : 'M',
							items :
							[
								[ '左对齐', 'left' ],
								[ '居中', 'center' ],
								[ '右对齐', 'right' ]
							],
							setup : function( element )
							{
								this.setValue( element.getAttribute( 'type' ) );
							}
						}
					]
				},
				{
					type : 'hbox',
					widths : ['50%','50%'],
					children : 
					[
						{
							id : 'txtFontSize',
							type : 'text',
							label : '字体大小',
							accessKey : 'V'
						},
						{
							id : 'txtMax',
							type : 'text',
							label : '最多字符数',
							'default' : '20'
						}
					]
				},
				{
					type : 'hbox',
					widths : ['50%','50%'],
					children : 
					[
						{
							id : 'txtSize',
							type : 'text',
							label : '输入框宽度',
							accessKey : 'V'
						},
						{
							id : 'txtHeight',
							type : 'text',
							label : '输入框高度'
						}
					]
				},
				{
					type : 'hbox',
					widths : ['50%','50%'],
					children : 
					[
						{
							id : 'txtDataType',
							type : 'select',
							label : '数据类型',
							items : 
							[   ['字符','char'],
								['整型','int'],
								['浮点型','float'],
								['日期型','date'],
								['电子邮件','email'],
								['固定电话型','tel'],
								['移动电话型','mobile']
							]
						},
						{
							id : 'txtMin',
							type : 'text',
							label : '最少字符数',
							'default' : '5'
						}
					]
				},
				{
					id : 'txtValue',
					type : 'text',
					label : '默认值'
				}
			];
	
	var dataSelect = [['---选择数据源---','0']];
	if(typeof(MODULE_CONFIG) !== 'undefined'){
		for(var key in MODULE_CONFIG){
			var item = MODULE_CONFIG[key];
			dataSelect.push([item,key]);
		}
		elements.push({
					id : 'module_field',
					type : 'select',
					widths : ['5%','100px'],
					label : '业务表单字段：',
					labelLayout : 'horizontal',
					labelStyle : 'font-weight:bold',
					style : 'width:150px',
					'default' : '0',
					items : dataSelect
				});
	}

	return {
		title : '单行输入框属性',
		minWidth : 250,
		minHeight : 200,
		resizable : false,
		onShow : function()
		{
			delete this.textField;

			var element = this.getParentEditor().getSelection().getSelectedElement();
			if ( element && element.getName() == "input" &&
					( acceptedTypes[ element.getAttribute( 'type' ) ] || !element.getAttribute( 'type' ) ) )
			{
				this.textField = element;
				this.setupContent( element );
			}
			
			if(element){
				//给表单赋值
				this.getContentElement('info','txtName').setValue(element.getAttribute('title'));
				this.getContentElement('info','txtAlign').setValue(element.getAttribute('align'));
				this.getContentElement('info','txtMax').setValue(element.getAttribute('maxLength'));
				this.getContentElement('info','txtSize').setValue(element.getAttribute('size'));
				this.getContentElement('info','txtValue').setValue(element.getAttribute('value'));
				
				//样式属性
				var styleString = element.getAttribute('style');
				if(styleString){
					var styleArray = styleString.split(';');
					var txtFontSize = '',txtHeight = '',txtAlign = '';
					for(var i = 0; i < styleArray.length; i++){
						var valueArray = styleArray[i].split(':');
						
						if(valueArray[0].indexOf('font-size') >= 0 || valueArray[0].indexOf('FONT-SIZE') >= 0){
							txtFontSize = valueArray[1].substr(0,valueArray[1].length - 2);
						}
						
						if(valueArray[0].indexOf('height') >= 0 || valueArray[0].indexOf('HEIGHT') >= 0){
							txtHeight = valueArray[1].substr(0,valueArray[1].length - 2);
						}
						if(valueArray[0].indexOf('text-align') >= 0 || valueArray[0].indexOf('TEXT-ALIGN') >= 0){
							txtAlign = valueArray[1];
						}
					}
					this.getContentElement('info','txtFontSize').setValue(txtFontSize);
					this.getContentElement('info','txtHeight').setValue(txtHeight);
					this.getContentElement('info','txtAlign').setValue(txtAlign);
				}
				
				//验证属性
				var validString = element.getAttribute('data_valid');
				var txtDataType = '',txtMin = '';
				if(validString){
					var validArray = validString.split(';');
					for(var i = 0; i < validArray.length; i++){
						var valueArray = validArray[i].split(':');
						if(valueArray[0] === 'type'){
							txtDataType = valueArray[1];
						}
						if(valueArray[0] === 'minLen'){
							txtMin = valueArray[1];
						}
					}
					this.getContentElement('info','txtDataType').setValue(txtDataType);
					this.getContentElement('info','txtMin').setValue(txtMin);
				}
				if(typeof(MODULE_CONFIG) !== 'undefined'){
					this.getContentElement('info','module_field').setValue(element.getAttribute('module_field'));
				}
			}
		},
		onOk : function()
		{
			var editor,
				element = this.textField,
				isInsertMode = !element;

			if ( isInsertMode )
			{
				editor = this.getParentEditor();
				element = editor.document.createElement( 'input' );
				element.setAttribute( 'type', 'text' );
			}
			
			//设置元素的值
			element.setAttribute('title',this.getContentElement('info','txtName').getValue());
			
			var txtAlign = this.getContentElement('info','txtAlign').getValue();
			var txtFontSize = this.getContentElement('info','txtFontSize').getValue();
			var txtHeight = this.getContentElement('info','txtHeight').getValue();
			var styleString = '';
			if(txtAlign){
				element.setAttribute('align',this.getContentElement('info','txtAlign').getValue());
				styleString += ('text-align:' + txtAlign + ';');
			}
			if(txtFontSize){
				styleString += ('font-size:' + txtFontSize + 'px;');
			}
			if(txtHeight){
				styleString += ('height:' + txtHeight + 'px;');
			}
			element.setAttribute('style',styleString);

			if(this.getContentElement('info','txtMax').getValue() != ''){
				element.setAttribute('maxlength',this.getContentElement('info','txtMax').getValue());
			} else {
				element.setAttribute('maxlength','10');
			}
			element.setAttribute('size',this.getContentElement('info','txtSize').getValue());
			element.setAttribute('value',this.getContentElement('info','txtValue').getValue());
			
			//数据验证
			var dataType = this.getContentElement('info','txtDataType').getValue();
			var txtMin = this.getContentElement('info','txtMin').getValue();
			var validString = '';
			if(dataType){
				validString += ('type:' + dataType + ';');
			}
			if(txtMin){
				validString += ('minLen:' + txtMin + ';');
			}
			element.setAttribute('data_valid',validString);
			
			if(isInsertMode){
				var element_index = 1/*XD_FORM_ELEMENT_INDEX()*/;
				
				element.setAttribute('name','DATA_' + element_index);
				element.setAttribute('id','DATA_' + element_index);
			}
			
			element.setAttribute('element_type','xd_textfield');
			
			if(typeof(MODULE_CONFIG) !== 'undefined'){
				element.setAttribute('module_field',this.getContentElement('info','module_field').getValue());
			}
			
			/*
			var txtHidden = this.getContentElement('info','hidden').getValue();
			if(txtHidden){
				element.setAttribute( 'type', 'hidden' );
			}*/
			
			//设置
			
			if ( isInsertMode ){
				editor.insertElement( element );
				//editor.insertHtml( element );
			}
			this.commitContent( { element : element } );
		},
		onLoad : function()
		{
			
		},
		contents : [
			{
				id : 'info',
				label : '表单元素',
				title : '单行文本框',
				elements : elements
			}
		]
	};
});
