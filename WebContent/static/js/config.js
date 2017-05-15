// seajs 配置
seajs.config({
	charset: 'utf-8',
	// 根目录
	base: '/charging/',

	// 路径配置
	paths: {
		'GLOBAL'	: 'static/global',
		// JS 目录
		'JS'		: 'static/js',
		// JS 插件
		'Plugins'	: 'static/plugins',
		// CSS 目录
		'CSS'		: 'static/css'
	},

	// 别名配置
	alias: {
		'jquery'	: 'Plugins/jquery-1.10.2.min.js', // jquery
		'global'	: 'JS/global.js?v=0.2', // global.js
		'Bootstrap'	: 'Plugins/bootstrap/js/bootstrap.min.js', // bootstrap.js
		'Amazeui'	: 'Plugins/amazeui/js/amazeui.js', // amazeui.js
	},
	// 预加载模块
	preload: ['jquery','global']
});