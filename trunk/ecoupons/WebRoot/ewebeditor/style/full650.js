config.FixWidth = "";
config.StyleUploadDir = "uploadfile/";
config.InitMode = "EDIT";
config.AutoDetectPasteFromWord = "1";
config.BaseUrl = "1";
config.BaseHref = "";
config.AutoRemote = "1";
config.ShowBorder = "0";
config.StateFlag = "1";
config.SBCode = "1";
config.SBEdit = "1";
config.SBText = "1";
config.SBView = "1";
config.EnterMode = "1";
config.Skin = "office2003";
config.AutoDetectLanguage = "1";
config.DefaultLanguage = "zh-cn";
//�Ƿ���������������ļ�
config.AllowBrowse = "0";
config.AllowImageSize = "200000";
config.AllowFlashSize = "1024000";
config.AllowMediaSize = "1024000";
config.AllowFileSize = "1024000";
config.AllowRemoteSize = "200000";
config.AllowLocalSize = "200000";
config.AllowImageExt = "gif|jpg|jpeg|bmp|png|JPG|JPEG|GIF|BMP|PNG|tif|iff";
config.AllowFlashExt = "swf|flv|swi";
config.AllowMediaExt = "mid|midi|wav|mp3|rmi|cda|avi|mpg|mpeg|ra|ram|wov|asf|asx|rm|wma|mov";
config.AllowFileExt = "rar|zip|pdf|doc|xls|ppt|chm|hlp|ppt|PPT";
config.AllowRemoteExt = "gif|jpg|jpeg|bmp|png|JPG|JPEG|GIF|BMP|PNG|tif|iff";
config.ServerExt = "asp";

config.Toolbars = [
	["TBHandle", "FormatBlock", "FontName", "FontSize", "Cut", "Copy", "Paste", "PasteText", "FindReplace", "Delete", "RemoveFormat", "TBSep", "FindReplace", "SpellCheck", "TBSep", "UnDo", "ReDo", "TBSep", "SelectAll", "UnSelect", "TBSep", "absolutePosition", "zIndexBackward", "zIndexForward"],
	["TBHandle", "Bold", "Italic", "UnderLine", "StrikeThrough", "SuperScript", "SubScript", "UpperCase", "LowerCase", "TBSep", "JustifyLeft", "JustifyCenter", "JustifyRight", "JustifyFull", "TBSep", "OrderedList", "UnOrderedList", "Indent", "Outdent", "TBSep", "ForeColor", "BackColor", "TBSep", "BgColor", "BackImage", "TBSep", "Fieldset", "Iframe", "HorizontalRule", "Marquee", "TBSep", "CreateLink", "Anchor", "Map", "Unlink"],
	["TBHandle", "Image", "Flash", "Media", "File", "TBSep", "TableMenu", "FormMenu", "TBSep", "RemoteUpload", "LocalUpload", "ImportWord", "ImportExcel", "TBSep", "BR", "Paragraph", "ParagraphAttr", "TBSep", "Symbol", "Emot", "Art", "NowDate", "NowTime", "Excel", "Quote", "TBSep", "PrintBreak", "Print", "TBSep", "ShowBorders", "ZoomMenu", "Refresh", "Maximize", "About"],
	["TBHandle", "FontMenu", "ParagraphMenu", "ComponentMenu", "ObjectMenu", "ToolMenu", "FileMenu", "TBSep", "TableMenu", "TableInsert", "TableProp", "TableCellProp", "TableCellSplit", "TableRowProp", "TableRowInsertAbove", "TableRowInsertBelow", "TableRowMerge", "TableRowSplit", "TableRowDelete", "TableColInsertLeft", "TableColInsertRight", "TableColMerge", "TableColSplit", "TableColDelete", "TBSep", "FormMenu", "FormText", "FormTextArea", "FormRadio", "FormCheckbox", "FormDropdown", "FormButton"],
	["TBHandle", "TBSep", "GalleryImage", "GalleryFlash", "GalleryMedia", "GalleryFile", "TBSep", "Code", "EQ", "TBSep", "Big", "Small", "TBSep", "ModeCode", "ModeEdit", "ModeText", "ModeView", "TBSep", "SizePlus", "SizeMinus", "TBSep", "ZoomSelect", "TBSep", "Template", "FontSizeMenu", "FontNameMenu", "FormatBlockMenu", "TBSep", "Site"]
];
/*config.Toolbars = [
	["TBHandle", "FormatBlock", "FontName", "FontSize", "Cut", "Copy", "Paste", "PasteText", "FindReplace", "Delete", "RemoveFormat", "TBSep", "FindReplace", "SpellCheck", "TBSep", "UnDo", "ReDo", "TBSep", "SelectAll", "UnSelect", "TBSep", "absolutePosition", "zIndexBackward", "zIndexForward"],
	["TBHandle", "Bold", "Italic", "UnderLine", "StrikeThrough", "SuperScript", "SubScript", "UpperCase", "LowerCase", "TBSep", "JustifyLeft", "JustifyCenter", "JustifyRight", "JustifyFull", "TBSep", "OrderedList", "UnOrderedList", "Indent", "Outdent", "TBSep", "ForeColor", "BackColor", "TBSep", "BgColor", "BackImage", "TBSep", "Fieldset", "Iframe", "HorizontalRule", "Marquee", "TBSep", "CreateLink", "Anchor", "Map", "Unlink"],
	["TBHandle", "Image", "Flash", "Media", "File", "GalleryMenu", "TBSep", "TableMenu", "FormMenu", "TBSep", "RemoteUpload", "LocalUpload", "ImportWord", "ImportExcel", "TBSep", "BR", "Paragraph", "ParagraphAttr", "TBSep", "Symbol", "Emot", "Art", "NowDate", "NowTime", "Excel", "Quote", "TBSep", "PrintBreak", "Print", "TBSep", "ShowBorders", "ZoomMenu", "Refresh", "Maximize", "About"],
	["TBHandle", "FontMenu", "ParagraphMenu", "ComponentMenu", "ObjectMenu", "ToolMenu", "FileMenu", "TBSep", "TableMenu", "TableInsert", "TableProp", "TableCellProp", "TableCellSplit", "TableRowProp", "TableRowInsertAbove", "TableRowInsertBelow", "TableRowMerge", "TableRowSplit", "TableRowDelete", "TableColInsertLeft", "TableColInsertRight", "TableColMerge", "TableColSplit", "TableColDelete", "TBSep", "FormMenu", "FormText", "FormTextArea", "FormRadio", "FormCheckbox", "FormDropdown", "FormButton"],
	["TBHandle", "TBSep", "GalleryMenu", "GalleryImage", "GalleryFlash", "GalleryMedia", "GalleryFile", "TBSep", "Code", "EQ", "TBSep", "Big", "Small", "TBSep", "ModeCode", "ModeEdit", "ModeText", "ModeView", "TBSep", "SizePlus", "SizeMinus", "TBSep", "ZoomSelect", "TBSep", "Template", "FontSizeMenu", "FontNameMenu", "FormatBlockMenu", "TBSep", "Site"]
];*/
