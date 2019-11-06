package ru.ange.mhb.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.ange.mhb.dao.rs.BotUserRSExtractor;
import ru.ange.mhb.pojo.user.BotUserExtended;

@Component
public class BotUserDAO {

    //private static final BotUserMapper BOT_USER_MAPPER = new BotUserMapper();

    private static final ResultSetExtractor<BotUserExtended> BOT_USER_EXTRACTOR = new BotUserRSExtractor();

    @Autowired
    private NamedParameterJdbcTemplate npjdbc;

//    private static final String ADD_USER =
//                    "insert into moviehatbot.Users values (" +
//                    "    default, :telUserId, :alias, :language, :adult " +
//                    ")";
//
//    public BotUserBasic addBotUser(BotUserBasic botUser) throws DataAccessException {
//        MapSqlParameterSource params = new MapSqlParameterSource();
//        params.addValue("telUserId", botUser.getTelUserId() );
//        params.addValue("alias", botUser.getAlias() );
//        params.addValue("language", botUser.getLanguage() );
//        params.addValue("adult", botUser.isAdult() );
//
//        GeneratedKeyHolder holder = new GeneratedKeyHolder();
//        npjdbc.update( ADD_USER, params, holder );
//
//        return botUser.setId( holder.getKey().intValue() );
//    }

//    private static final String GET_USER_BY_ID =
//                    "select id, telUserId, alias, language, adult " +
//                    "from moviehatbot.Users " +
//                    "where id = :id";
//
//    public BotUserExtended getBotUser(int id) throws DataAccessException {
//        MapSqlParameterSource params = new MapSqlParameterSource();
//        params.addValue("id", id );
//        return npjdbc.queryForObject( GET_USER_BY_ID, params, BOT_USER_MAPPER );
//    }

    private static final String GET_USER_BY_TEL_ID =
                    "select " +
                    "  us.id           as us_id, " +
                    "  us.telUserId    as us_telUserId, " +
                    "  us.alias        as us_alias, " +
                    "  us.language     as us_language, " +
                    "  us.adult        as us_adult, " +
                    "  fl.id           as fl_id, " +
                    "  fl.name         as fl_name, " +
                    "  fl.date         as fl_date, " +
                    "  fl.createUserId as fl_createUserId, " +
                    "  fm.id           as fm_id, " +
                    "  fm.name         as fm_name, " +
                    "  fm.tmdbId       as fm_tmdbId, " +
                    "  fm.addDate      as fm_addDate, " +
                    "  fm.favListId    as fm_favListId, " +
                    "  fm.addUserId    as fm_addUserId, " +
                    "  fm.watched      as fm_watched, " +
                    "  fm.rating       as fm_rating " +
                    "from " +
                    "  moviehatbot.Users as us " +
                    "  left join moviehatbot.UsersToFavLists as utfl on us.id = utfl.userId " +
                    "  left join moviehatbot.FavLists as fl on utfl.favListId = fl.id " +
                    "  left join moviehatbot.FavMovies as fm on fm.favListId = fl.id " +
                    "where  " +
                    "  us.telUserId = :telUserId " +
                    "order by  " +
                    "   us.id,  " +
                    "   fl.id, " +
                    "   fm.id ";

    public BotUserExtended getBotUserByTelUserId(int telUserId) throws DataAccessException {

        System.out.println("telUserId = " + telUserId);

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("telUserId", telUserId );
        return npjdbc.query( GET_USER_BY_TEL_ID, params, BOT_USER_EXTRACTOR );
    }
}
