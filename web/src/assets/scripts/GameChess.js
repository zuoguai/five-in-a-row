import { GameObject } from "./GameObject";


export class GameChess extends GameObject {
    constructor(info, gamemap) {
        super();

        this.id = info.id;
        this.color = info.color;
        this.gamemap = gamemap;

        this.next_chess = null;
        this.status = "wait"; //wait代表等待对方，move表示可以下子，die表示失败，win表示获胜


        this.step = 0;

    }

    start() {
        if (this.id === 1) this.status = "move";
    }


    updata() {
        this.render();
    }

    render() {

        const black = "#000000";
        const white = "#ffffff";
        const L = this.gamemap.L;
        const ctx = this.gamemap.ctx;

        this.chess = this.gamemap.chess;


        for (const chess of this.chess) {
            if (chess.id === 1) ctx.fillStyle = black;
            else ctx.fillStyle = white;
            ctx.beginPath();
            ctx.arc(chess.r * L, chess.c * L, L * 0.49, 0, Math.PI * 2);
            ctx.fill();

        }


    }

}