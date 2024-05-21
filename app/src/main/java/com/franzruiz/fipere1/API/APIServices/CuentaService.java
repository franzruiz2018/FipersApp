package com.franzruiz.fipere1.API.APIServices;

import com.franzruiz.fipere1.Model.CategoriaMovimiento;
import com.franzruiz.fipere1.Model.Cuenta;
import com.franzruiz.fipere1.Model.EstadoCuenta;
import com.franzruiz.fipere1.Model.Request.GenericRequest;
import com.franzruiz.fipere1.Model.Request.LoginRequest;
import com.franzruiz.fipere1.Model.Request.OperacionRequest;
import com.franzruiz.fipere1.Model.Request.PlanificacionMensualRegistroRequest;
import com.franzruiz.fipere1.Model.Request.PlanificacionMensualRequest;
import com.franzruiz.fipere1.Model.Respons.GenericRespons;
import com.franzruiz.fipere1.Model.Respons.DeudaRespons;
import com.franzruiz.fipere1.Model.Respons.MovimientoTipoRespons;
import com.franzruiz.fipere1.Model.Respons.OperacionesRespons;
import com.franzruiz.fipere1.Model.Respons.PlanificacionMensualRespons;
import com.franzruiz.fipere1.Model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.POST;


public interface CuentaService {

    @POST("usuario/login")
    Call<Usuario> getLogin(@Body LoginRequest loginRequest);

    @POST("cuenta/buscar")
    Call<Cuenta> getCuenta(@Body Cuenta cuenta);

    @POST("cuenta/listar")
    Call<List<Cuenta>> getListarCuentas(@Body Usuario usuario);

    @POST("cuenta/listar_para_transferir")
    Call<List<Cuenta>> getListarCuentasParaTransferir(@Body GenericRequest genericRequest);

    @POST("cuenta/listar_para_deuda")
    Call<List<Cuenta>> getListarCuentasParaPagarDeudas(@Body GenericRequest genericRequest);


    @POST("cuenta/listar_tipomov_por_tipocuenta")
    Call<List<CategoriaMovimiento>> getListarTipoMov_por_cuenta(@Body Cuenta cuenta);

    @POST("cuenta/operaciones")
    Call<List<OperacionesRespons>> getOperaciones(@Body Cuenta cuenta);


    @POST("operacion/registrar")
    Call<GenericRespons> getRegistrarOperaciones(@Body OperacionRequest operacionRequest );

    @POST("cuenta/listar_movimientos_por_categoria")
    Call<List<MovimientoTipoRespons>> getlistar_movimientos_por_categoria_gasto(@Body GenericRequest genericRequest );

    @POST("cuenta/operaciones_por_tipo_movimiento_y_fecha")
    Call<List<OperacionesRespons>> getOperaciones_por_tipo_movimiento_y_fecha(@Body GenericRequest genericRequest );

    @POST("cuenta/listar_movimientos_para_planificacion")
    Call<List<MovimientoTipoRespons>> getlistar_movimientos_para_planificacion();



    @POST("operacion/archivar")
    Call<GenericRespons> getAnularOperaciones(@Body OperacionRequest operacionRequest );

    @POST("cuenta/listar_deudas")
    Call<List<DeudaRespons>> getlistar_deudas_por_cuenta(@Body GenericRequest genericRequest );

    @POST("egresos_planificados/lista_egresos_planificados_vs_real")
    Call<List<PlanificacionMensualRespons>> getlistar_egresos_planificados_vs_real(@Body PlanificacionMensualRequest planificacionMensualRequest );

    @POST("egresos_planificados/egresos_planificados_registrar")
    Call<GenericRespons> getEgresos_planificados_registrar(@Body PlanificacionMensualRegistroRequest planificacionMensualRegistroRequest );

    @POST("reporte/listar_estado_cuenta")
    Call<List<EstadoCuenta>> getListar_estado_de_cuenta(@Body PlanificacionMensualRequest planificacionMensualRequest );


}
