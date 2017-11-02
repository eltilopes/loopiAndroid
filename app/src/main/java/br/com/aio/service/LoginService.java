package br.com.aio.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import br.com.aio.endpoint.LoginEndPoint;
import br.com.aio.entity.Categoria;
import br.com.aio.entity.Especialidade;
import br.com.aio.entity.SubCategoria;
import br.com.aio.entity.Token;
import br.com.aio.entity.UsuarioSession;
import br.com.aio.utils.JsonUtils;
import br.com.aio.utils.SessionUtils;
import br.com.aio.utils.UsuarioSharedUtils;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

import static br.com.aio.utils.BundleUtils.PREFS_NAME;

/**
 * Created by elton on 11/10/2017.
 */

public class LoginService extends ValidadorCallBack {

    public static LoginEndPoint service;

    private static final String SCOPE = "read";
    private static final String CLIENT_ID = "aio";
    private static final String CLIENT_SECRET = "lamperouge";
    private static final String GRANT_TYPE = "password";

    public LoginService(Context context){
        super(context);        service = restAdapter.create(LoginEndPoint.class);    }

    public void login(String login, String senha){
        Response response = service.login(login, senha, SCOPE, CLIENT_ID, CLIENT_SECRET, GRANT_TYPE);
        JsonUtils jsonUtil = new JsonUtils<Token>();
        Token token = (Token)jsonUtil.converteObject(response, new TypeToken<Token>(){}.getType());
        if(response.getStatus() == Status.SUCESSO.getStatus()) {
            UsuarioSharedUtils.setUsuarioShared(new UsuarioSession(token.getUser().getId(), login, senha, token.getUser().getNome(), token.getAccess_token(),token.getIdUsuarioGlpi(), token.getUser().getCpf(), token.getRoles()), ctx);
            SharedPreferences mPrefs = ctx.getSharedPreferences(PREFS_NAME, 0);
            carregarCategorias(mPrefs);
            carregarSubCategorias(mPrefs);
            carregarEspecialidades(mPrefs);
        }else{
            throw new RuntimeException();
        }
    }

    private void carregarCategorias(SharedPreferences mPrefs) {
        CategoriaService categoriaService = new CategoriaService(ctx);
        List<Categoria> categorias = categoriaService.getCategorias();
        if(categorias!= null){
            SessionUtils.setCategorias(mPrefs, categorias);
        }
    }

    private void carregarSubCategorias(SharedPreferences mPrefs) {
        SubCategoriaService subCategoriaService= new SubCategoriaService(ctx);
        List<SubCategoria> subCategorias = subCategoriaService.getSubCategorias();
        if(subCategorias!= null){
            SessionUtils.setSubCategorias(mPrefs, subCategorias);
        }
    }

    private void carregarEspecialidades(SharedPreferences mPrefs) {
        EspecialidadeService especialidadeService= new EspecialidadeService(ctx);
        List<Especialidade> especialidades = especialidadeService.getEspecialidades();
        if(especialidades!= null){
            SessionUtils.setEspecialidades(mPrefs, especialidades);
        }
    }

    public void reLogin(){
        UsuarioSession usuario = UsuarioSharedUtils.getUsuarioShared(ctx);
        login(usuario.getLogin(), usuario.getSenha());
    }
    public Response cadastroUsuario(String email, String password, String cpf){
        return service.cadastrarUsuario(email, password, cpf);
    }

    public Response solicitarAlteracaoSenha(String novaSenha, String cpf) {
        return service.solicitarAlteracaoSenha(novaSenha, cpf);
    }
    public Response alterarDadosdoUsuario(String email, String novaSenha){
        String token = UsuarioSharedUtils.getElementoSalvo(ctx, UsuarioSharedUtils.Preferences.PREFERENCES_TOKEN);
        String id = UsuarioSharedUtils.getElementoSalvo(ctx, UsuarioSharedUtils.Preferences.PREFERENCES_ID);
        String cpf = UsuarioSharedUtils.getElementoSalvo(ctx, UsuarioSharedUtils.Preferences.PREFERENCES_CPF);
        if("".equals(novaSenha)){
            novaSenha = UsuarioSharedUtils.getElementoSalvo(ctx, UsuarioSharedUtils.Preferences.PREFERENCES_PASSWORD);
        }
        Response resp = service.atualizarUsuario("Bearer " + token, id, email, cpf, novaSenha);
        String response =   new String(((TypedByteArray)resp.getBody()).getBytes());
        UsuarioSharedUtils.set(ctx, UsuarioSharedUtils.Preferences.PREFERENCES_TOKEN, response.replace("\"", ""));
        return resp;
    }
    public Response logout(){
        String token = UsuarioSharedUtils.getElementoSalvo(ctx, UsuarioSharedUtils.Preferences.PREFERENCES_TOKEN);
        String login = UsuarioSharedUtils.getElementoSalvo(ctx, UsuarioSharedUtils.Preferences.PREFERENCES_LOGIN);
        return service.logout("Bearer " + token, login);
    }


}
