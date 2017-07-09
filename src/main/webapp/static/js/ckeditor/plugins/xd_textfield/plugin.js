/**
 * 单行输入框插件
 * @author tony
 */
CKEDITOR.plugins.add('xd_textfield',
{
	icons: 'xd_textfield',
	init : function( editor )
	{
		var pluginName = 'xd_textfield';
		editor.addCommand(pluginName,new CKEDITOR.dialogCommand(pluginName));
		editor.ui.addButton('xd_textfield',{
											label:'单行输入框',
											toolbar: 'insert',
											command:pluginName
										});
		
		CKEDITOR.dialog.add(pluginName,this.path + 'dialogs/xd_textfield.js?20170707');
	}
});
