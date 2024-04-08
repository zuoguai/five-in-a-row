import { GameChess } from "./GameChess";
import { GameObject } from "./GameObject";
import { Chess } from "./Chess";

export class GameMap extends GameObject {
    constructor(ctx, parent, store) {
        super();
        this.ctx = ctx;
        this.parent = parent;
        this.L = 0;
        this.store = store;

        this.rows = 13;
        this.cols = 13;

        this.player = [
            new GameChess({ id: 1, color: "#000000" }, this),
            new GameChess({ id: 2, color: "#ffffff" }, this)
        ]

        this.chess = [];
        this.step = 1;


    }

    start() {

        this.L = parseInt(Math.min(this.parent.clientWidth / this.cols, this.parent.clientHeight / this.rows));
        this.ctx.canvas.width = this.L * this.cols;
        this.ctx.canvas.height = this.L * this.rows;

        this.add_listening_events();

    }
    check_valid(chess) {

        for (const st of this.chess) {
            if (st.c === chess.c && st.r === chess.r) {
                console.log("该位置已经有棋子");
                return false;
            }
        }
        return true;
    }


    add_listening_events() {
        this.ctx.canvas.focus();
        const canvas = this.ctx.canvas;


        this.ctx.canvas.addEventListener("click", e => {



            if (this.store.state.user.id == this.store.state.pk.next) {
                let x = e.pageX - canvas.offsetLeft;
                let y = e.pageY - canvas.offsetTop;
                const L = this.L;

                x = parseInt(x / L + 0.5);
                y = parseInt(y / L + 0.5);
                // console.log(x)
                // console.log(y)

                if (x > 0 && x < this.cols && y > 0 && y < this.rows) {
                    let chess = new Chess(x, y, 0);
                    if (this.check_valid(new Chess(x, y, 0))) {
                        // this.chess[this.chess.length] = chess;
                        // const flag = this.check_win();
                        // if (flag !== 9) {
                        //     console.log("玩家", flag, "取得了胜利")
                        // } else {
                        //     this.player[idx].status = "move";
                        //     this.player[1 - idx].status = "wait";
                        //     this.step++;
                        // }
                        
                        // console.log(this.store.state.pk.a_id + "和" + this.store.state.user.id)
                        if (this.store.state.pk.a_id == this.store.state.user.id) {
                            this.store.state.pk.socket.send(JSON.stringify({
                                event: "move",
                                chess_x: chess.r,
                                chess_y: chess.c,
                                chess_id: 1,
                            }))
                            // console.log("1发送")
                        } else {
                            this.store.state.pk.socket.send(JSON.stringify({
                                event: "move",
                                chess_x: chess.r,
                                chess_y: chess.c,
                                chess_id: 2,
                            }))
                            // console.log("2发送")

                        }
                        this.store.commit("updateStep", {
                            next: 0
                        })
                        this.store.commit("updateTimeout", 24);
                    }
                    // console.log(x, y);
                }
            }
        })
    }
    setChess(x, y, id) {
        this.chess[this.chess.length] = new Chess(x, y, id);
    }
    updata() {

        this.rander();

    }

    rander() {

        const color_even = "#87ceff";
        const color_odd = "#f0eab6";

        for (let r = 0; r < this.rows; r++) {
            for (let c = 0; c < this.cols; c++) {
                if ((r + c) % 2 === 0) {
                    this.ctx.fillStyle = color_even;
                } else {
                    this.ctx.fillStyle = color_odd;
                }
                this.ctx.fillRect(c * this.L, r * this.L, this.L, this.L);

            }
        }

        // for(let chess of this.chess){

        // }

    }

    // check_win() {
    //     let site = [];
    //     for (let i = 0; i < this.cols; i++) {
    //         site[i] = [];
    //         for (let j = 0; j < this.rows; j++)site[i][j] = 9;

    //     }


    //     for (const chess of this.chess) {
    //         site[chess.c][chess.r] = chess.id;
    //     }

    //     let last = 9;
    //     let sum = 1;
    //     //判断列
    //     for (let i = 1; i < this.cols; i++) {
    //         for (let j = 1; j < this.rows; j++) {
    //             if (site[i][j] !== 9) {
    //                 if (last === site[i][j]) sum++;
    //                 else sum = 1;
    //             } else sum = 1;
    //             last = site[i][j];
    //             if (sum === 5) return last;
    //         }
    //     }

    //     last = 9;
    //     sum = 1;
    //     //判断行
    //     for (let i = 1; i < this.cols; i++) {
    //         for (let j = 1; j < this.rows; j++) {
    //             if (site[j][i] !== 9) {
    //                 if (last === site[j][i]) sum++;
    //                 else sum = 1;
    //             } else sum = 1;
    //             last = site[j][i];
    //             if (sum === 5) return last;
    //         }
    //     }

    //     last = 9;
    //     sum = 1;
    //     //判断反对角线
    //     for (let i = 6; i < 33; i++) {
    //         for (let j = 1; j < i && j <= 18; j++) {
    //             if (i - j > 18) continue;
    //             if (site[j][i - j] !== 9) {
    //                 if (last === site[j][i - j]) sum++;
    //                 else sum = 1;
    //             } else sum = 1;
    //             last = site[j][i - j];
    //             if (sum === 5) {
    //                 console.log(sum, "sum................")
    //                 return last;
    //             }
    //         }
    //     }

    //     last = 9;
    //     sum = 1;
    //     //判断对角线
    //     for (let i = -13; i < 14; i++) {
    //         for (let j = 18; j >= 1; j--) {
    //             if (j + i < 1 || j + i > 18) continue;
    //             if (site[j][j + i] !== 9) {
    //                 if (last === site[j][i + j]) sum++;
    //                 else sum = 1;
    //             } else sum = 1;
    //             last = site[j][i + j];
    //             if (sum === 5) return last;
    //         }
    //     }



    //     return 9;
    // }






}




