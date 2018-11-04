@SpringBootApplication
@EnableRatpack
public class App {

    @Autowired
    private Content content;

    @Autowired
    private ArticleList list;

    @Bean
    public Action<Chain> home() {
        return chain -> chain.get(ctx -> ctx.render(content.body()));
    }

    public static void main(String[] args) {
        SpringApplication.run(EmbedRatpackApp.class, args);
    }
}