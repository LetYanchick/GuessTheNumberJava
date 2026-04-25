package com.example.game;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GameController {
    // Открытие главной страницы
    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        GameSession game = getOrCreateGame(session);
        model.addAttribute("gamesCount", game.getGamesCount());
        return "index";
    }

    // Начало новой игры
    @GetMapping("/start")
    public String startGame(HttpSession session) {
        GameSession game = getOrCreateGame(session);
        game.newGame();
        session.setAttribute("game", game);
        return "redirect:/play";
    }

    // Страница игры
    @GetMapping("/play")
    public String playPage(HttpSession session, Model model) {
        GameSession game = getOrCreateGame(session);
        model.addAttribute("attempts", game.getAttempts());
        return "play";
    }

    // Обработка введённого числа
    @PostMapping("/guess")
    public String guess(@RequestParam(required = false) Integer number,
                        HttpSession session,
                        Model model) {

        GameSession game = getOrCreateGame(session);

        // Требование 4 — проверка что число в диапазоне 0-100
        if (number == null || number < 0 || number > 100) {
            model.addAttribute("error", "Введите число от 0 до 100!");
            model.addAttribute("attempts", game.getAttempts());
            return "play";
        }

        // Увеличиваем счётчики попыток
        game.setAttempts(game.getAttempts() + 1);
        game.setTotalAttempts(game.getTotalAttempts() + 1);

        if (number < game.getSecretNumber()) {
            // Требование 6 — число меньше загаданного
            model.addAttribute("hint", "Загаданное число больше!");
            model.addAttribute("attempts", game.getAttempts());
            return "play";
        } else if (number > game.getSecretNumber()) {
            // Требование 6 — число больше загаданного
            model.addAttribute("hint", "Загаданное число меньше!");
            model.addAttribute("attempts", game.getAttempts());
            return "play";
        } else {
            // Требование 7 — угадал!
            game.setGameWon(true);
            session.setAttribute("game", game);
            model.addAttribute("secretNumber", game.getSecretNumber());
            model.addAttribute("attempts", game.getAttempts());
            return "win";
        }
    }

    // Конец игры — показ общих результатов
    @GetMapping("/end")
    public String endGame(HttpSession session, Model model) {
        GameSession game = getOrCreateGame(session);
        model.addAttribute("gamesCount", game.getGamesCount());
        model.addAttribute("totalAttempts", game.getTotalAttempts());
        session.invalidate(); // Требование 9.3 — результаты не сохраняются
        return "end";
    }

    // Вспомогательный метод — достаём игру из сессии или создаём новую
    private GameSession getOrCreateGame(HttpSession session) {
        GameSession game = (GameSession) session.getAttribute("game");
        if (game == null) {
            game = new GameSession();
            session.setAttribute("game", game);
        }
        return game;
    }
}
