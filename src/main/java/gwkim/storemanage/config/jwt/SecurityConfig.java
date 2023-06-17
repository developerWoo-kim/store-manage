package gwkim.storemanage.config.jwt;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/**", "/login", "/h2-console/**", "/").permitAll()
//                .antMatchers("/admin/**").authenticated() // 인증 필요한 URL 패턴
                .anyRequest().permitAll()
//                .anyRequest().authenticated()
                .and() // 인증 없이 접근 가능한 엔드포인트 설정
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
