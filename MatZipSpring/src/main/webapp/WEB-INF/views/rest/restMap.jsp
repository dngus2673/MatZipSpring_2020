<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" language="java" %>
<style>
.label { margin-bottom: 96px; cursor: pointer; }
.label * { display: inline-block;vertical-align: top; }
.label .left { background: url("https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_l.png") no-repeat;display: inline-block;height: 24px;overflow: hidden;vertical-align: top;width: 7px;}
.label .center { background: url(https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_bg.png) repeat-x;display: inline-block;height: 24px;font-size: 20px;line-height: 24px;}
.label .right { background: url("https://t1.daumcdn.net/localimg/localimages/07/2011/map/storeview/tip_r.png") -1px 0  no-repeat;display: inline-block;height: 24px;overflow: hidden;width: 6px;}
</style>
<div id="sectionContainerCenter">
	<div id="mapContainer" style="width: 80vw;height: 50vw;"></div>
	
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=1965125d8bfdc12035fae1f11ca346bd"></script>
	<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
	
	<script>
		var markerList = [] //마커 리스트
		const options = { //지도를 생성할 때 필요한 기본 옵션
			center: new kakao.maps.LatLng(35.865559, 128.593409), // 지도의 중심좌표
			level: 5 // 지도의 레벨 (확대, 축소 정보)
		};
		

		const map = new kakao.maps.Map(mapContainer, options);
		
		
		function getRestaurantList() {
			//마커 모두 지우기
			markerList.forEach(function(marker) {
				marker.setMap(null)
			})
			const bounds = map.getBounds();
			const southWest = bounds.getSouthWest()
			const northEast = bounds.getNorthEast()
			
			console.log('southWest : ' + southWest)
			console.log('northEast : ' + northEast)
			
			const sw_lat = southWest.getLat()
			const sw_lng = southWest.getLng()
			const ne_lat = northEast.getLat()
			const ne_lng = northEast.getLng()
			
			axios.get('/rest/ajaxGetList', {
				params: { 
					sw_lat, sw_lng, ne_lat, ne_lng 
					}
			}).then(function(res) {
					console.log(res.data)
					
					res.data.forEach(function(item) {
						createMarker(item)	
					})
				})
			}
		
		kakao.maps.event.addListener(map, 'tilesloaded', getRestaurantList)
		
		// 마커 생성
		function createMarker(item) {
			
			var content = document.createElement('div')
			content.className = 'label'
			
			var leftSpan = document.createElement('span')
			leftSpan.className = 'left'
			
			var rightSpan = document.createElement('span')
			rightSpan.className = 'right'
			
			var centerSpan = document.createElement('span')
			centerSpan.className = 'center'
			
			var restNm = item.nm
			if(item.is_favorite == 1){
				restNm += ' ♥'
			}

			centerSpan.innerText = restNm
			
			content.appendChild(leftSpan)
			content.appendChild(centerSpan)
			content.appendChild(rightSpan)
			
			//var ctnt = `<div class ="label"><span class="left"></span><span class="center">\${item.nm}</span><span class="right"></span></div>`;
			
			var mPos = new kakao.maps.LatLng(item.lat, item.lng)
			
			var marker = new kakao.maps.Marker({ position: mPos });
			
			var customOverlay = new kakao.maps.CustomOverlay({
				position: mPos,
				content: content
			});

			// 마커에 클릭이벤트를 등록합니다
			addEvent(content, 'click', function() {
				console.log('marker click : ' + item.i_rest)
				moveDetail(item.i_rest)
			});
			customOverlay.setMap(map)
			marker.setMap(map)
			markerList.push(marker)
		}
		
		function moveDetail(i_rest) {
			location.href = '/rest/detail?i_rest='+ i_rest
		}
		
		function addEvent(target, type, callback) { //IE 때문에 사용
			if(target.addEventListener){
				target.addEventListener(type, callback);
			}else {
				target.attachEvent('on' + type, callback);
			}
		}
		
		if(navigator.geolocation){
			console.log('Geolocation is supported!')
			var startPos;
		
		//var geoSuccess = function(position) {}
		navigator.geolocation.getCurrentPosition(function(pos) {
			startPos = pos
						
			console.log('lat : ' + startPos.coords.latitude)
			console.log('lng : ' + startPos.coords.longitude)
			
			if(map){
				var moveLatLon = new kakao.maps.LatLng(startPos.coords.latitude, startPos.coords.longitude)
				map.panTo(moveLatLon)
			}
		});
		
		}else {
			console.log('Geolocation is not supported for this Browser/OS.')
		}
		
		//console.log(map.getCenter())
	</script>
</div>