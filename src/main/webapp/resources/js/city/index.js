function loadTable() {
	const tblCities = $('#tblCities');
	tblCities.bootstrapTable();
	
	$.ajax({
		url: '/sakila-servlet/city/findAll',
		type: 'GET',
		dataType: 'json',
		success: function(data) {
			const {estatus, datos, mensaje} = data;
			
			if (estatus === 'success') {
				tblCities
					.bootstrapTable('destroy')
					.bootstrapTable({data: datos});
			} else {
				bootbox.alert({
					message: mensaje,
					size: 'large'
				});
			}
		}
	});
}

// Funcion con javascript6
const addCity = () => {
	const btnAddCity = $('#btnAddCity');
	
	btnAddCity.click(function () {
		$.ajax({
			type: 'GET',
			url: '/sakila-servlet/city/city.html',
			contentType: 'application/html; charset=utf-8',
			success: function(html) {
				bootbox.dialog({
					title: 'Agregar Ciudad',
					onEscape: false,
					animate: true,
					message: html,
					buttons: {
						cancel:{
							label: 'Cancelar',
							className: 'btn-secondary'
						},
						save:{
							label: 'Agregar',
							className: 'btn-success',
							callback: function() {
								if ($('#txtCity').val() !== '' && $('#cmbPais').val() !== '') {
									save();
								} else {
									bootbox.alert('Debes introducir los valores');
								}
								
								return false;
							}
						}
					}
				});
			}
		});
	});
}

const save = () => {
	const frmCity = $('#frmCity');
	
	$.ajax({
		type: 'POST',
		url: '/sakila-servlet/city/save',
		data: frmCity.serialize(),
		dataType: 'json',
		success: function (data) {
			const {estatus, datos, mensaje} = data;
			
			if (estatus === 'success') {
				loadTable();
				$('.modal').modal('hide');
				bootbox.alert(`Se agrego la ciudad <b>${datos.city}</b> con el id <b>${datos.cityId}</b>`);
			} else {
				bootbox.alert(mensaje);
			}
		}
	});
}

const getFile = () => {
	const btnFile = $('#btnFile');
	
	btnFile.click(function() {
		$.ajax({
			type: 'GET',
			url: '/sakila-servlet/city/file',
			contentType: 'json',
			success: function(data) {
				const {estatus, datos, mensaje} = data;
			
				if (estatus === 'success') {
					const download = document.createElement('a');
					
					download.href = `data:text/plain;base64, ${datos}`;
					download.download = 'ciudades.txt';
					download.click();
				} else {
					bootbox.alert({
						message: mensaje,
						size: 'large'
					});
				}
			}
		});
	});
}

loadTable();
addCity();
getFile();
