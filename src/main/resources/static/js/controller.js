var competencyCtrls = angular.module('competencyCtrls', []);
// 第一步 选择领域
competencyCtrls.controller('domainCtrl', function($scope, $http, $location, myFactory) {
	$scope.domains = [];
	$scope.competencys = {};
	var p = myFactory.getPosition();
	$scope.position = p;
	$scope.loadDomains = function() {
		$http.get('/getDomains').then(function(response) {
			$scope.domains = response.data;
			myFactory.setDomains($scope.domains);
		});
	};

	$scope.domains = myFactory.getDomains();
	if (!$scope.domains) {
		$scope.loadDomains();
	} else {
		angular.forEach($scope.domains, function(domain){
			if(domain.checked){
				//$scope.competencys = domain.competencys;
				angular.forEach(domain.competencys, function(competency){
					var temp = $scope.competencys[competency.id];
					if(!temp){
						temp = {count:0,entity:competency};
						$scope.competencys[competency.id] = temp;
					}
					temp.count = temp.count + 1;
				});
			}
		});
	}

	$scope.checkDomain = function(domain) {
		domain.checked = !domain.checked;
		angular.forEach(domain.competencys, function(competency){
			var temp = $scope.competencys[competency.id];
			if(!temp){
				temp = {count:0,entity:competency};
				$scope.competencys[competency.id] = temp;
			}
			if(domain.checked){
				competency.firstChecked = true;
				temp.count = temp.count + 1;
			} else {
				temp.count = temp.count - 1;
				if(temp.count == 0){
					competency.firstChecked = false;
					delete $scope.competencys[competency.id];
				}
			}
			
		});
		
	};

	$scope.next = function() {
		if(!$scope.position || $scope.position == ''){
			toastr.warning('请输入职位！');
			return;
		}
		if(Object.getOwnPropertyNames($scope.competencys).length <= 0){
			toastr.warning('请选择职位关键贡献领域！');
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
		$scope.checkedCompetencys = [];
		myFactory.setCompetencys($scope.checkedCompetencys);
	}
	$scope.next = function() {
		var count = $scope.checkedCompetencys.length;
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

	$scope.classes = myFactory.getClasses();
	if(!$scope.classes && $scope.domains){
		$scope.classes = {};
		$http.get('/getCompetenctClasses').then(function(response) {
			angular.forEach(response.data, function(competencyClass) {
				$scope.classes[competencyClass.id] = competencyClass;
			});
			angular.forEach($scope.domains, function(domain) {
				angular.forEach(domain.competencys, function(competency) {
					competency.checked = false;
					$scope.classes[competency.competencyClass.id].competencys.push(competency);
				});
			});
			myFactory.setClasses($scope.classes);
		});
	}

	$scope.check = function(competency) {
		for(var i=0;i<$scope.checkedCompetencys.length;i++){
			if($scope.checkedCompetencys[i].id == competency.id){
				toastr.warning("该胜任力已选择！");
				return;
			}
		}
		competency.checked = true;
		$scope.checkedCompetencys.push(competency);
	};
	
	$scope.checkOut = function(competency) {
		competency.checked = false;
		for(var i=0;i<$scope.checkedCompetencys.length;i++){
			if($scope.checkedCompetencys[i].id == competency.id){
				$scope.checkedCompetencys.splice(i,1);
			}
		}
	};

});
// 第三步 导出
competencyCtrls.controller('exportCtrl', function($scope, $http, $location, myFactory) {
	$scope.prev = function() {
		myFactory.setCompetencys($scope.competencys);
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
		for(var i=0;i<$scope.competencys.length;i++){
			if($scope.competencys[i].id == id){
				$scope.competencys.splice(i,1);
				break;
			}
		}
		//$scope.competencys[id].checked = false;
	};
	
	$scope.export = function() {
		var exportData = [];
		angular.forEach($scope.competencys, function(competency) {
			if(competency.checked && competency.firstChecked){
				exportData.push({
					'name': competency.name,
					'definition': competency.definition,
					'description': competency.description
				});
			}
		});
		if(exportData.length == 0){
			toastr.warning('请选择要导出的胜任力！');
			return;
		}
		alasql.promise('SELECT * INTO XLSXML("' + $scope.position + '--胜任力模型",?) FROM ?',[mystyle, exportData]).then(function(data) {
			if (data == 1) {
				toastr.success('数据导出成功！');
			}
		});
	};
	
	$scope.newPosition = function(){
		myFactory.reset();
		$location.url("/domain");
	};
});
