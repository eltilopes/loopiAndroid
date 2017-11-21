package br.com.aio.service;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import br.com.aio.R;
import br.com.aio.endpoint.ServicoProfissionalEndPoint;
import br.com.aio.entity.Filtro;
import br.com.aio.entity.GoogleDirectionsResponse;
import br.com.aio.entity.ServicoCard;
import br.com.aio.entity.ServicoProfissional;
import br.com.aio.utils.DirectionUtils;
import br.com.aio.utils.FiltroUtils;
import br.com.aio.utils.JsonUtils;
import retrofit.client.Response;

/**
 * Created by elton on 11/10/2017.
 */

public class ServicoProfissionalService extends ValidadorCallBack {

    public static ServicoProfissionalEndPoint service;
    private static final String SERVICOS = "[\n" +
            "  {\n" +
            "    \"categoria\": {\n" +
            "      \"descricao\": \"Saúde\",\n" +
            "      \"id\": 1\n" +
            "    },\n" +
            "    \"descricao\": \"Dermatologia Completa\",\n" +
            "    \"distancia\": \"Distância não calculada\",\n" +
            "    \"distanciaMetros\": 0,\n" +
            "    \"duracao\": \"Tempo não calculado\",\n" +
            "    \"especialidade\": {\n" +
            "      \"descricao\": \"Dermatologista\",\n" +
            "      \"id\": 1,\n" +
            "      \"subCategoria\": {\n" +
            "        \"categoria\": {\n" +
            "          \"descricao\": \"Saúde\",\n" +
            "          \"id\": 1\n" +
            "        },\n" +
            "        \"descricao\": \"Médicos\",\n" +
            "        \"id\": 1\n" +
            "      }\n" +
            "    },\n" +
            "    \"estrelas\": 2,\n" +
            "    \"favorito\": false,\n" +
            "    \"id\": 3,\n" +
            "    \"latitude\": -3.75829,\n" +
            "    \"longitude\": -38.480949,\n" +
            "    \"preco\": 100.0,\n" +
            "    \"subCategoria\": {\n" +
            "      \"categoria\": {\n" +
            "        \"descricao\": \"Saúde\",\n" +
            "        \"id\": 1\n" +
            "      },\n" +
            "      \"descricao\": \"Médicos\",\n" +
            "      \"id\": 1\n" +
            "    },\n" +
            "    \"tempo\": 15,\n" +
            "    \"thumbnail\": \"http://urologistasanchotene.com.br/wp-content/uploads/2015/02/Foto-Urologista-Elton-Sanchotene.jpg\",\n" +
            "    \"title\": \"ELTON LOPES\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"categoria\": {\n" +
            "      \"descricao\": \"Saúde\",\n" +
            "      \"id\": 1\n" +
            "    },\n" +
            "    \"descricao\": \"Serviço Dermatologico\",\n" +
            "    \"distancia\": \"Distância não calculada\",\n" +
            "    \"distanciaMetros\": 0,\n" +
            "    \"duracao\": \"Tempo não calculado\",\n" +
            "    \"especialidade\": {\n" +
            "      \"descricao\": \"Dermatologista\",\n" +
            "      \"id\": 1,\n" +
            "      \"subCategoria\": {\n" +
            "        \"categoria\": {\n" +
            "          \"descricao\": \"Saúde\",\n" +
            "          \"id\": 1\n" +
            "        },\n" +
            "        \"descricao\": \"Médicos\",\n" +
            "        \"id\": 1\n" +
            "      }\n" +
            "    },\n" +
            "    \"estrelas\": 1,\n" +
            "    \"favorito\": false,\n" +
            "    \"id\": 2,\n" +
            "    \"latitude\": -3.75829,\n" +
            "    \"longitude\": -38.480949,\n" +
            "    \"preco\": 60.0,\n" +
            "    \"subCategoria\": {\n" +
            "      \"categoria\": {\n" +
            "        \"descricao\": \"Saúde\",\n" +
            "        \"id\": 1\n" +
            "      },\n" +
            "      \"descricao\": \"Médicos\",\n" +
            "      \"id\": 1\n" +
            "    },\n" +
            "    \"tempo\": 5,\n" +
            "    \"thumbnail\": \"http://urologistasanchotene.com.br/wp-content/uploads/2015/02/Foto-Urologista-Elton-Sanchotene.jpg\",\n" +
            "    \"title\": \"ELTON LOPES\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"categoria\": {\n" +
            "      \"descricao\": \"Saúde\",\n" +
            "      \"id\": 1\n" +
            "    },\n" +
            "    \"descricao\": \"gfhhh\",\n" +
            "    \"distancia\": \"Distância não calculada\",\n" +
            "    \"distanciaMetros\": 0,\n" +
            "    \"duracao\": \"Tempo não calculado\",\n" +
            "    \"especialidade\": {\n" +
            "      \"descricao\": \"Dermatologista\",\n" +
            "      \"id\": 1,\n" +
            "      \"subCategoria\": {\n" +
            "        \"categoria\": {\n" +
            "          \"descricao\": \"Saúde\",\n" +
            "          \"id\": 1\n" +
            "        },\n" +
            "        \"descricao\": \"Médicos\",\n" +
            "        \"id\": 1\n" +
            "      }\n" +
            "    },\n" +
            "    \"estrelas\": 3,\n" +
            "    \"favorito\": false,\n" +
            "    \"id\": 2051,\n" +
            "    \"latitude\": -3.75829,\n" +
            "    \"longitude\": -38.480949,\n" +
            "    \"preco\": 55.0,\n" +
            "    \"subCategoria\": {\n" +
            "      \"categoria\": {\n" +
            "        \"descricao\": \"Saúde\",\n" +
            "        \"id\": 1\n" +
            "      },\n" +
            "      \"descricao\": \"Médicos\",\n" +
            "      \"id\": 1\n" +
            "    },\n" +
            "    \"tempo\": 55,\n" +
            "    \"thumbnail\": \"http://urologistasanchotene.com.br/wp-content/uploads/2015/02/Foto-Urologista-Elton-Sanchotene.jpg\",\n" +
            "    \"title\": \"ELTON LOPES\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"categoria\": {\n" +
            "      \"descricao\": \"Saúde\",\n" +
            "      \"id\": 1\n" +
            "    },\n" +
            "    \"descricao\": \"fhbvv\",\n" +
            "    \"distancia\": \"Distância não calculada\",\n" +
            "    \"distanciaMetros\": 0,\n" +
            "    \"duracao\": \"Tempo não calculado\",\n" +
            "    \"especialidade\": {\n" +
            "      \"descricao\": \"Dermatologista\",\n" +
            "      \"id\": 1,\n" +
            "      \"subCategoria\": {\n" +
            "        \"categoria\": {\n" +
            "          \"descricao\": \"Saúde\",\n" +
            "          \"id\": 1\n" +
            "        },\n" +
            "        \"descricao\": \"Médicos\",\n" +
            "        \"id\": 1\n" +
            "      }\n" +
            "    },\n" +
            "    \"estrelas\": 15,\n" +
            "    \"favorito\": true,\n" +
            "    \"id\": 2052,\n" +
            "    \"latitude\": -3.75829,\n" +
            "    \"longitude\": -38.480949,\n" +
            "    \"preco\": 85.0,\n" +
            "    \"subCategoria\": {\n" +
            "      \"categoria\": {\n" +
            "        \"descricao\": \"Saúde\",\n" +
            "        \"id\": 1\n" +
            "      },\n" +
            "      \"descricao\": \"Médicos\",\n" +
            "      \"id\": 1\n" +
            "    },\n" +
            "    \"tempo\": 98,\n" +
            "    \"thumbnail\": \"http://urologistasanchotene.com.br/wp-content/uploads/2015/02/Foto-Urologista-Elton-Sanchotene.jpg\",\n" +
            "    \"title\": \"ELTON LOPES\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"categoria\": {\n" +
            "      \"descricao\": \"Saúde\",\n" +
            "      \"id\": 1\n" +
            "    },\n" +
            "    \"descricao\": \"Consulta Simples\",\n" +
            "    \"distancia\": \"Distância não calculada\",\n" +
            "    \"distanciaMetros\": 0,\n" +
            "    \"duracao\": \"Tempo não calculado\",\n" +
            "    \"especialidade\": {\n" +
            "      \"descricao\": \"Dermatologista\",\n" +
            "      \"id\": 1,\n" +
            "      \"subCategoria\": {\n" +
            "        \"categoria\": {\n" +
            "          \"descricao\": \"Saúde\",\n" +
            "          \"id\": 1\n" +
            "        },\n" +
            "        \"descricao\": \"Médicos\",\n" +
            "        \"id\": 1\n" +
            "      }\n" +
            "    },\n" +
            "    \"estrelas\": 3,\n" +
            "    \"favorito\": true,\n" +
            "    \"id\": 22,\n" +
            "    \"latitude\": -3.741433,\n" +
            "    \"longitude\": -38.499196,\n" +
            "    \"preco\": 99.0,\n" +
            "    \"subCategoria\": {\n" +
            "      \"categoria\": {\n" +
            "        \"descricao\": \"Saúde\",\n" +
            "        \"id\": 1\n" +
            "      },\n" +
            "      \"descricao\": \"Médicos\",\n" +
            "      \"id\": 1\n" +
            "    },\n" +
            "    \"tempo\": 15,\n" +
            "    \"thumbnail\": \"https://scontent.ffor13-1.fna.fbcdn.net/v/t1.0-9/1506029_749113131846854_4502479123257974854_n.jpg?oh\\u003df317cdf04616822340bbbea85c826daa\\u0026oe\\u003d5A6DB6DF\",\n" +
            "    \"title\": \"RENAN ERICK\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"categoria\": {\n" +
            "      \"descricao\": \"Saúde\",\n" +
            "      \"id\": 1\n" +
            "    },\n" +
            "    \"descricao\": \"Consulta\",\n" +
            "    \"distancia\": \"Distância não calculada\",\n" +
            "    \"distanciaMetros\": 0,\n" +
            "    \"duracao\": \"Tempo não calculado\",\n" +
            "    \"especialidade\": {\n" +
            "      \"descricao\": \"Dermatologista\",\n" +
            "      \"id\": 1,\n" +
            "      \"subCategoria\": {\n" +
            "        \"categoria\": {\n" +
            "          \"descricao\": \"Saúde\",\n" +
            "          \"id\": 1\n" +
            "        },\n" +
            "        \"descricao\": \"Médicos\",\n" +
            "        \"id\": 1\n" +
            "      }\n" +
            "    },\n" +
            "    \"estrelas\": 3,\n" +
            "    \"favorito\": true,\n" +
            "    \"id\": 21,\n" +
            "    \"latitude\": -3.754706,\n" +
            "    \"longitude\": -38.528856,\n" +
            "    \"preco\": 120.0,\n" +
            "    \"subCategoria\": {\n" +
            "      \"categoria\": {\n" +
            "        \"descricao\": \"Saúde\",\n" +
            "        \"id\": 1\n" +
            "      },\n" +
            "      \"descricao\": \"Médicos\",\n" +
            "      \"id\": 1\n" +
            "    },\n" +
            "    \"tempo\": 15,\n" +
            "    \"thumbnail\": \"https://media.licdn.com/mpr/mpr/shrinknp_200_200/AAEAAQAAAAAAAA0hAAAAJGY0Yjg0YTdkLWZhNTgtNDMwNC05MTkyLWQzMjlkMWRiZmUwZg.jpg\",\n" +
            "    \"title\": \"RODGER MAIA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"categoria\": {\n" +
            "      \"descricao\": \"Saúde\",\n" +
            "      \"id\": 1\n" +
            "    },\n" +
            "    \"descricao\": \"Consulta Geral\",\n" +
            "    \"distancia\": \"Distância não calculada\",\n" +
            "    \"distanciaMetros\": 0,\n" +
            "    \"duracao\": \"Tempo não calculado\",\n" +
            "    \"especialidade\": {\n" +
            "      \"descricao\": \"Clínico Geral\",\n" +
            "      \"id\": 4,\n" +
            "      \"subCategoria\": {\n" +
            "        \"categoria\": {\n" +
            "          \"descricao\": \"Saúde\",\n" +
            "          \"id\": 1\n" +
            "        },\n" +
            "        \"descricao\": \"Médicos\",\n" +
            "        \"id\": 1\n" +
            "      }\n" +
            "    },\n" +
            "    \"estrelas\": 3,\n" +
            "    \"favorito\": true,\n" +
            "    \"id\": 24,\n" +
            "    \"latitude\": -3.741433,\n" +
            "    \"longitude\": -38.499196,\n" +
            "    \"preco\": 99.0,\n" +
            "    \"subCategoria\": {\n" +
            "      \"categoria\": {\n" +
            "        \"descricao\": \"Saúde\",\n" +
            "        \"id\": 1\n" +
            "      },\n" +
            "      \"descricao\": \"Médicos\",\n" +
            "      \"id\": 1\n" +
            "    },\n" +
            "    \"tempo\": 15,\n" +
            "    \"thumbnail\": \"https://scontent.ffor13-1.fna.fbcdn.net/v/t1.0-9/1506029_749113131846854_4502479123257974854_n.jpg?oh\\u003df317cdf04616822340bbbea85c826daa\\u0026oe\\u003d5A6DB6DF\",\n" +
            "    \"title\": \"RENAN ERICK\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"categoria\": {\n" +
            "      \"descricao\": \"Saúde\",\n" +
            "      \"id\": 1\n" +
            "    },\n" +
            "    \"descricao\": \"Consulta Ginecologista\",\n" +
            "    \"distancia\": \"Distância não calculada\",\n" +
            "    \"distanciaMetros\": 0,\n" +
            "    \"duracao\": \"Tempo não calculado\",\n" +
            "    \"especialidade\": {\n" +
            "      \"descricao\": \"Ginecologista\",\n" +
            "      \"id\": 5,\n" +
            "      \"subCategoria\": {\n" +
            "        \"categoria\": {\n" +
            "          \"descricao\": \"Saúde\",\n" +
            "          \"id\": 1\n" +
            "        },\n" +
            "        \"descricao\": \"Médicos\",\n" +
            "        \"id\": 1\n" +
            "      }\n" +
            "    },\n" +
            "    \"estrelas\": 3,\n" +
            "    \"favorito\": true,\n" +
            "    \"id\": 23,\n" +
            "    \"latitude\": -3.754706,\n" +
            "    \"longitude\": -38.528856,\n" +
            "    \"preco\": 156.0,\n" +
            "    \"subCategoria\": {\n" +
            "      \"categoria\": {\n" +
            "        \"descricao\": \"Saúde\",\n" +
            "        \"id\": 1\n" +
            "      },\n" +
            "      \"descricao\": \"Médicos\",\n" +
            "      \"id\": 1\n" +
            "    },\n" +
            "    \"tempo\": 15,\n" +
            "    \"thumbnail\": \"https://media.licdn.com/mpr/mpr/shrinknp_200_200/AAEAAQAAAAAAAA0hAAAAJGY0Yjg0YTdkLWZhNTgtNDMwNC05MTkyLWQzMjlkMWRiZmUwZg.jpg\",\n" +
            "    \"title\": \"RODGER MAIA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"categoria\": {\n" +
            "      \"descricao\": \"Alimentação\",\n" +
            "      \"id\": 2\n" +
            "    },\n" +
            "    \"descricao\": \"Sashimi\",\n" +
            "    \"distancia\": \"Distância não calculada\",\n" +
            "    \"distanciaMetros\": 0,\n" +
            "    \"duracao\": \"Tempo não calculado\",\n" +
            "    \"especialidade\": {\n" +
            "      \"descricao\": \"Sushi\",\n" +
            "      \"id\": 7,\n" +
            "      \"subCategoria\": {\n" +
            "        \"categoria\": {\n" +
            "          \"descricao\": \"Beleza\",\n" +
            "          \"id\": 3\n" +
            "        },\n" +
            "        \"descricao\": \"Manicure\",\n" +
            "        \"id\": 3\n" +
            "      }\n" +
            "    },\n" +
            "    \"estrelas\": 3,\n" +
            "    \"favorito\": true,\n" +
            "    \"id\": 28,\n" +
            "    \"latitude\": -3.75829,\n" +
            "    \"longitude\": -38.480949,\n" +
            "    \"preco\": 33.0,\n" +
            "    \"subCategoria\": {\n" +
            "      \"categoria\": {\n" +
            "        \"descricao\": \"Alimentação\",\n" +
            "        \"id\": 2\n" +
            "      },\n" +
            "      \"descricao\": \"Restaurante\",\n" +
            "      \"id\": 2\n" +
            "    },\n" +
            "    \"tempo\": 15,\n" +
            "    \"thumbnail\": \"https://abrilmundoestranho.files.wordpress.com/2016/08/4fb41d0e98276814260000b9chef-chapeu-cozinha-faca.jpg?quality\\u003d70\\u0026strip\\u003dall\\u0026strip\\u003dinfo\",\n" +
            "    \"title\": \"ELTON LOPES\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"categoria\": {\n" +
            "      \"descricao\": \"Alimentação\",\n" +
            "      \"id\": 2\n" +
            "    },\n" +
            "    \"descricao\": \"Lasanha\",\n" +
            "    \"distancia\": \"Distância não calculada\",\n" +
            "    \"distanciaMetros\": 0,\n" +
            "    \"duracao\": \"Tempo não calculado\",\n" +
            "    \"especialidade\": {\n" +
            "      \"descricao\": \"Comida\",\n" +
            "      \"id\": 3,\n" +
            "      \"subCategoria\": {\n" +
            "        \"categoria\": {\n" +
            "          \"descricao\": \"Alimentação\",\n" +
            "          \"id\": 2\n" +
            "        },\n" +
            "        \"descricao\": \"Restaurante\",\n" +
            "        \"id\": 2\n" +
            "      }\n" +
            "    },\n" +
            "    \"estrelas\": 3,\n" +
            "    \"favorito\": true,\n" +
            "    \"id\": 27,\n" +
            "    \"latitude\": -3.75829,\n" +
            "    \"longitude\": -38.480949,\n" +
            "    \"preco\": 99.99,\n" +
            "    \"subCategoria\": {\n" +
            "      \"categoria\": {\n" +
            "        \"descricao\": \"Alimentação\",\n" +
            "        \"id\": 2\n" +
            "      },\n" +
            "      \"descricao\": \"Restaurante\",\n" +
            "      \"id\": 2\n" +
            "    },\n" +
            "    \"tempo\": 15,\n" +
            "    \"thumbnail\": \"https://abrilmundoestranho.files.wordpress.com/2016/08/4fb41d0e98276814260000b9chef-chapeu-cozinha-faca.jpg?quality\\u003d70\\u0026strip\\u003dall\\u0026strip\\u003dinfo\",\n" +
            "    \"title\": \"ELTON LOPES\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"categoria\": {\n" +
            "      \"descricao\": \"Beleza\",\n" +
            "      \"id\": 3\n" +
            "    },\n" +
            "    \"descricao\": \"Pintar Pé\",\n" +
            "    \"distancia\": \"Distância não calculada\",\n" +
            "    \"distanciaMetros\": 0,\n" +
            "    \"duracao\": \"Tempo não calculado\",\n" +
            "    \"especialidade\": {\n" +
            "      \"descricao\": \"Pé\",\n" +
            "      \"id\": 6,\n" +
            "      \"subCategoria\": {\n" +
            "        \"categoria\": {\n" +
            "          \"descricao\": \"Alimentação\",\n" +
            "          \"id\": 2\n" +
            "        },\n" +
            "        \"descricao\": \"Restaurante\",\n" +
            "        \"id\": 2\n" +
            "      }\n" +
            "    },\n" +
            "    \"estrelas\": 3,\n" +
            "    \"favorito\": true,\n" +
            "    \"id\": 26,\n" +
            "    \"latitude\": -3.831123,\n" +
            "    \"longitude\": -38.484197,\n" +
            "    \"preco\": 55.0,\n" +
            "    \"subCategoria\": {\n" +
            "      \"categoria\": {\n" +
            "        \"descricao\": \"Beleza\",\n" +
            "        \"id\": 3\n" +
            "      },\n" +
            "      \"descricao\": \"Manicure\",\n" +
            "      \"id\": 3\n" +
            "    },\n" +
            "    \"tempo\": 15,\n" +
            "    \"thumbnail\": \"http://www.gazetadopovo.com.br/viver-bem/wp-content/uploads/2017/05/cabeleireira.jpg\",\n" +
            "    \"title\": \"MARIA ROSA\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"categoria\": {\n" +
            "      \"descricao\": \"Beleza\",\n" +
            "      \"id\": 3\n" +
            "    },\n" +
            "    \"descricao\": \"Pintar Mão\",\n" +
            "    \"distancia\": \"Distância não calculada\",\n" +
            "    \"distanciaMetros\": 0,\n" +
            "    \"duracao\": \"Tempo não calculado\",\n" +
            "    \"especialidade\": {\n" +
            "      \"descricao\": \"Mão\",\n" +
            "      \"id\": 2,\n" +
            "      \"subCategoria\": {\n" +
            "        \"categoria\": {\n" +
            "          \"descricao\": \"Beleza\",\n" +
            "          \"id\": 3\n" +
            "        },\n" +
            "        \"descricao\": \"Manicure\",\n" +
            "        \"id\": 3\n" +
            "      }\n" +
            "    },\n" +
            "    \"estrelas\": 3,\n" +
            "    \"favorito\": true,\n" +
            "    \"id\": 25,\n" +
            "    \"latitude\": -3.831123,\n" +
            "    \"longitude\": -38.484197,\n" +
            "    \"preco\": 40.0,\n" +
            "    \"subCategoria\": {\n" +
            "      \"categoria\": {\n" +
            "        \"descricao\": \"Beleza\",\n" +
            "        \"id\": 3\n" +
            "      },\n" +
            "      \"descricao\": \"Manicure\",\n" +
            "      \"id\": 3\n" +
            "    },\n" +
            "    \"tempo\": 15,\n" +
            "    \"thumbnail\": \"http://www.gazetadopovo.com.br/viver-bem/wp-content/uploads/2017/05/cabeleireira.jpg\",\n" +
            "    \"title\": \"MARIA ROSA\"\n" +
            "  }\n" +
            "]";

    public ServicoProfissionalService(Context context) {
        super(context);
        service = restAdapter.create(ServicoProfissionalEndPoint.class);
    }

    public List<ServicoCard>  salvarServicoProfissional(ServicoProfissional servicoProfissional) {
        Response response = service.salvarServicoProfissional(servicoProfissional);
        JsonUtils<List<ServicoCard>> json = new JsonUtils<List<ServicoCard>>();
        List<ServicoCard> servicos = json.converteObject(response, new TypeToken<List<ServicoCard>>() {
        }.getType());
        return servicos;

    }

    public List<ServicoCard> getServicoCardPorFiltro(Filtro filtro) {/*
        String token = UsuarioSharedUtils.getElementoSalvo(ctx, UsuarioSharedUtils.Preferences.PREFERENCES_TOKEN);
        String login = UsuarioSharedUtils.getElementoSalvo(ctx, UsuarioSharedUtils.Preferences.PREFERENCES_LOGIN);*/
        Response response = service.getServicoCardPorFiltro( filtro);
        JsonUtils<List<ServicoCard>> json = new JsonUtils<List<ServicoCard>>();
        List<ServicoCard> servicos = json.converteObject(response, new TypeToken<List<ServicoCard>>() {
        }.getType());
        servicos = servicos == null ? new ArrayList<ServicoCard>() : servicos;
        /*Gson gson = new Gson();
        List<ServicoCard> servicos = gson.fromJson(SERVICOS, new TypeToken<List<ServicoCard>>() {}.getType());*/
        return prepareServicoCards(servicos,getCtx(), filtro );
    }

    private static List<ServicoCard> prepareServicoCards(List<ServicoCard> servicos, Context context, Filtro filtro) {
        if(!servicos.isEmpty()) {
            DirectionUtils directionUtils = new DirectionUtils(context);
            GoogleDirectionsResponse googleDirectionsResponse;
            for (ServicoCard sc : servicos) {
                googleDirectionsResponse = directionUtils.getGoogleDirectionsResponse(filtro.getMinhaLatLng(), new LatLng(sc.getLatitude(), sc.getLongitude()));
                sc.setDistancia(googleDirectionsResponse != null ? googleDirectionsResponse.getDistance() : context.getString(R.string.distancia_nao_calculada));
                sc.setDuracao(googleDirectionsResponse != null ? context.getString(R.string.em_ate) + " " + googleDirectionsResponse.getDuration() : context.getString(R.string.tempo_nao_calculado));
                sc.setDistanciaMetros(googleDirectionsResponse != null ? googleDirectionsResponse.getDistanceMeters() : 0);
                sc.setQuantidadeFavorito(sc.getId().intValue());
            }
            servicos = FiltroUtils.ordenarServicosPorDistancia(servicos, filtro);
        }
        return servicos;
    }


}
