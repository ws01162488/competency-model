var competencyCtrls = angular.module('competencyCtrls', []);
// 第一步 选择领域
competencyCtrls.controller('domainCtrl', function($scope, $http, $location, myFactory) {
	$scope.domains = [];
	$scope.competencys = [];
	$scope.position == '';

	$scope.loadDomains = function() {
		$http.get('/getDomains').then(function(response) {
			$scope.domains = response.data;
			myFactory.setDomains($scope.domains);
		});
	};

	$scope.domains = myFactory.getDomains();
	if (!$scope.domains) {
		$scope.loadDomains();
	}

	$scope.checkDomain = function(domain) {
		domain.checked = !domain.checked;
		$scope.competencys = domain.competencys;
	};

	$scope.next = function() {
		if(!$scope.position || $scope.position == ''){
			toastr.warning('请输入职位！');
			return;
		}
		myFactory.setPosition($scope.position);
		$location.url("/class");
	};
});
//第二部 根据类型选择胜任力
competencyCtrls.controller('classCtrl', function($scope, $http, $location, myFactory) {
	$scope.prev = function() {
		$location.url("/domain");
	};

	$scope.checkedCompetencys = myFactory.getCompetencys();
	if (!$scope.checkedCompetencys) {
		$scope.checkedCompetencys = {};
	}
	$scope.next = function() {
		var count = Object.getOwnPropertyNames($scope.checkedCompetencys).length;
		console.log(count);
		if(count <= 0){
			toastr.warning('请选择胜任力！');
			return;
		}
		if(count > 9){
			toastr.warning('最多选出9项胜任力！');
			return;
		}
		myFactory.setCompetencys($scope.checkedCompetencys);
		$location.url("/export");
	};

	$scope.domains = myFactory.getDomains();
	$scope.position = myFactory.getPosition();
	if (!$scope.domains) {
		$scope.prev();
	}

	$scope.classes = {};
	$http.get('/getCompetenctClasses').then(function(response) {
		angular.forEach(response.data, function(competencyClass) {
			$scope.classes[competencyClass.id] = competencyClass;
		});
		angular.forEach($scope.domains, function(domain) {
			if (domain.checked) {
				angular.forEach(domain.competencys, function(competency) {
					competency.checked = false;
					$scope.classes[competency.competencyClass.id].competencys.push(competency);
				});
			}
		});
	});


	$scope.check = function(competency) {
		competency.checked = !competency.checked;
		if (competency.checked) {
			$scope.checkedCompetencys[competency.id] = competency;
		} else {
			delete $scope.checkedCompetencys[competency.id];
		}

	};

});
// 第三步 导出
competencyCtrls.controller('exportCtrl', function($scope, $http, $location, myFactory) {
	$scope.prev = function() {
		$location.url("/class");
	}
	$scope.position = myFactory.getPosition();

    var mystyle = {
    	headers: true,
    	column: {style:{Font:{Bold:'1',Size:'18'}},Height:30},
    	columns: [{
    			columnid: 'name',
    			title: '胜任力'
    		},
    		{
    			columnid: 'definition',
    			title: '定义',
    			width: 500,
    			height: 300
    		},
    		{
    			columnid: 'description',
    			title: '行为描述',
    			width: 500,
    			height: 300
    		}
    	]
    };

	$scope.competencys = myFactory.getCompetencys();
	if (!$scope.competencys) {
		$scope.prev();
	}
	
	$scope.delete = function(id){
		delete $scope.competencys[id];
	};
	
	$scope.modify = function(){
		
	};

	$scope.export = function() {
		var exportData = [];
		angular.forEach($scope.competencys, function(competency) {
			exportData.push({
				'name': competency.name,
				'definition': competency.definition,
				'description': competency.description
			});
		});
		if(exportData.length == 0){
			toastr.warning('请选择要导出的胜任力！');
			return;
		}
		alasql.promise('SELECT * INTO XLSXML("' + $scope.position + '",?) FROM ?',[mystyle, exportData]).then(function(data) {
			if (data == 1) {
				toastr.success('数据导出成功！');
			}
		});
	};
});