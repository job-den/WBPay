package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
public class CountController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/count")
    public String input(Model model) {

       int selectCount = jdbcTemplate.query("select count(1) from dtr_sys_pwb_payment", new ResultSetExtractor<Integer>() {
                @Override
                public Integer extractData(ResultSet rs) throws SQLException {
                    int result = 0;
                    if (rs.next())
                        result = rs.getInt(1);
                    return result;
                }
            });
        model.addAttribute("selectCount", selectCount);

        List<Map<String, Object>> mapList = jdbcTemplate.queryForList("select * from dtr_sys_pwb_payment");
        int rowCount = mapList.size();
        model.addAttribute("rowCount", rowCount);

        return "countForm";
    }

}