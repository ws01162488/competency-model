var app = angular.module('competencyApp', ['ngRoute', 'xeditable', 'competencyCtrls']);
// 存放页面间跳转需要传递的数据
app.run(function(editableOptions) {
	editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be also 'bs2', 'default'
});
app.factory('myFactory', function() {
	var domains;
	var competencys;
	var position;
	var classes;

	var setDomains = function(data) {
		domains = data;
	};

	var getDomains = function() {
		return domains;
	};
	
	var setCompetencys = function(data) {
		competencys = data;
	};

	var getCompetencys = function() {
		return competencys;
	};
	
	var setPosition = function(data) {
		position = data;
	};

	var getPosition = function() {
		return position;
	};
	
	var setClasses = function(data) {
		classes = data;
	};

	var getClasses = function() {
		return classes;
	};
	
	var reset = function() {
		domains = undefined;
		competencys = undefined;
		position = undefined;
		classes = undefined;
	};

	return {
		setDomains : setDomains,
		getDomains : getDomains,
		setCompetencys : setCompetencys,
		getCompetencys : getCompetencys,
		setClasses : setClasses,
		getClasses : getClasses,
		setPosition : setPosition,
		getPosition : getPosition,
		reset : reset
	}
});

app.config(function ($routeProvider) {
    $routeProvider.when('/domain', {
        templateUrl: '/view/domain',
        controller: 'domainCtrl'
    }).when('/class', {
        templateUrl: '/view/class',
        controller: 'classCtrl'
    }).when('/warrper', {
        templateUrl: '/view/warrper',
        controller: 'warrperCtrl'
    }).when('/evaluation', {
        templateUrl: '/view/evaluation',
        controller: 'evaluationCtrl'
    }).otherwise({
        redirectTo: '/domain'
    })
});

var replaceAll = function(str, token, newToken) {
	var i = -1;
	if (typeof(token) === "string") {
		while ((i = str.indexOf(token, i >= 0 ? i + newToken.length : 0)) !== -1) {
			str = str.substring(0, i).concat(newToken).concat(str.substring(i + token.length));
		}
	}
	return str;
};

app.filter('nl2br', ['$sce', function ($sce) {
    return function (text) {
    	console.log(replaceAll(text, '\\n', '<br/>'));
        return text ? $sce.trustAsHtml(replaceAll(text, '\\n', '<br/>')) : '';
    };
}]);
