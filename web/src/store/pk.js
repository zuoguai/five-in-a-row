

export default {
    state: {
        status: "matching",//matching 表示匹配，playing表示对战,
        socket: null,
        opponent_username: "",
        opponent_photo: "",
        opponent_rating: "150",
        a_id: 0,
        b_id: 0,
        next: 0,
        chess_x: 0,
        chess_y: 0,
        chess_id: 0,
        gameObject: null,
        loser: "none",//all, A, B ,none
        timeout: 24,


    },
    getters: {
    },
    mutations: {

        updateTimeout(state, timeout) {
            state.timeout = timeout;
        },
        updateSocket(state, socket) {
            state.socket = socket;
        },
        updateOpponent(state, opponent) {
            state.opponent_username = opponent.opponent_username;
            state.opponent_photo = opponent.opponent_photo;
            state.opponent_rating = opponent.opponent_rating;
        },
        updateStatus(state, status) {
            state.status = status;
        },
        updateGame(state, game) {
            state.a_id = game.a_id;
            state.b_id = game.b_id;
            state.next = game.next;
        },
        updateGameObject(state, gameObject) {
            state.gameObject = gameObject;
        },
        updateLoser(state, loser) {
            state.loser = loser;
        },
        updateStep(state, step) {
            state.chess_x = step.chess_x;
            state.chess_y = step.chess_y;
            state.chess_id = step.chess_id;
            state.next = step.next;
        }

    },
    actions: {


    },
    modules: {

    }
}