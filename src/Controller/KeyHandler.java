package Controller;

import Graphics.Sprite;
import Main.GamePanel;
import Entities.Player;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed = false;
    public boolean facingLeft, facingRight, facingUp, facingDown = false;//Duy
    public boolean bombPressed = false;
    public boolean bulletPressed = false;
    public boolean restartPressed = false;
    GamePanel gp;
    public boolean checkDrawTime = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0)
                    gp.ui.commandNum = 2;

            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2)
                    gp.ui.commandNum = 0;
            }
            if (code == KeyEvent.VK_ENTER) {
                gp.playStop(0);
                gp.playSE(1);
                //gp.playMusic(2);
                if (gp.ui.commandNum == 0) {
                    gp.currentLevel = 1;
                    gp.gameState = gp.chooseState;
                }
                if (gp.ui.commandNum == 2) {
                    System.exit(0);
                }
            }
        }

        if (gp.gameState == gp.chooseState) {
            if (code == KeyEvent.VK_A) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0)
                    gp.ui.commandNum = 2;

            }
            if (code == KeyEvent.VK_D) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2)
                    gp.ui.commandNum = 0;
            }
            if (code == KeyEvent.VK_W) {
                gp.playStop(0);
                gp.playSE(1);
                //gp.playMusic(2);
                if (gp.ui.commandNum == 0) {
                    gp.setScore(0);
                    gp.currentLevel = 1;
                    gp.restartGame(gp.getCurrentLevel());
                }
                if (gp.ui.commandNum == 1) {
                    gp.setScore(0);
                    gp.currentLevel = 1;
                    Sprite sprite = new Sprite(gp.playerB, 32, 48);
                    gp.player.setPlayerImage(sprite);
                    gp.restartGame(gp.getCurrentLevel());
                }
                if (gp.ui.commandNum == 2) {
                    gp.setScore(0);
                    gp.currentLevel = 1;
                    Sprite sprite = new Sprite(gp.playerR, 32, 48);
                    gp.player.setPlayerImage(sprite);
                    gp.restartGame(gp.getCurrentLevel());
                }

            }
        }

        if (gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_W) {
                //gp.playSE(2);
                upPressed = true;
                facingLeft = false; //Duy
                facingRight = false;
                facingDown = false;
                facingUp = true;//
            }
            if (code == KeyEvent.VK_S) {

               // gp.playSE(2);
                downPressed = true;
                facingLeft = false;
                facingRight = false; //Duy - huong bullet
                facingDown = true;
                facingUp = false;
            }
            if (code == KeyEvent.VK_A) {

              //  gp.playSE(2);
                leftPressed = true;
                facingLeft = true;
                facingRight = false;
                facingDown = false;
                facingUp = false;
            }
            if (code == KeyEvent.VK_D) {

             //   gp.playSE(2);
                rightPressed = true;
                facingLeft = false;
                facingRight = true;
                facingDown = false;
                facingUp = false;
            }
            if (code == KeyEvent.VK_T) {
                if (checkDrawTime == false) {
                    checkDrawTime = true;
                } else
                    checkDrawTime = false;
            }
            if (code == KeyEvent.VK_SPACE) {
                bombPressed = true;
            }

            if (code == KeyEvent.VK_E) {
                if (gp.objectManagement.currentBullets > 0) gp.playMusic(8);
                bulletPressed = true; //Duy
            }
            if (code == KeyEvent.VK_P) {
                if (gp.gameState == gp.playState) {
                    gp.gameState = gp.pauseState;
                    gp.timer.stop();
                } else if (gp.gameState == gp.pauseState) {
                    gp.gameState = gp.playState;
                    gp.timer.start();
                }
            }
        }

        if (gp.gameState == gp.pauseState) {

            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0)
                    gp.ui.commandNum = 2;
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2)
                    gp.ui.commandNum = 0;
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    if (gp.gameState == gp.playState) {
                        gp.gameState = gp.pauseState;
                        gp.timer.stop();
                    } else if (gp.gameState == gp.pauseState) {
                        gp.gameState = gp.playState;
                        gp.timer.start();
                    }
                }
                if (gp.ui.commandNum == 1) {
                    restartPressed = true;
                    gp.restartGame(gp.getCurrentLevel());
                }
                if (gp.ui.commandNum == 2) {
                    gp.gameState = gp.titleState;
                }
            }

        }

        if (gp.gameState == gp.loseState) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0)
                    gp.ui.commandNum = 1;
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 1)
                    gp.ui.commandNum = 0;
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    restartPressed = true;
                    gp.lose = false;
                    gp.restartGame(gp.getCurrentLevel());
                }
                if (gp.ui.commandNum == 1) {
                    gp.gameState = gp.titleState;
                    gp.lose = false;
                }
            }
        }

        if (gp.gameState == gp.winState) {
            if (gp.getCurrentLevel() == 1) {
                if (code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum < 0)
                        gp.ui.commandNum = 1;
                }
                if (code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum > 1)
                        gp.ui.commandNum = 0;
                }
                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum == 0) {
                        gp.currentLevel += 1;
                        gp.restartGame(gp.getCurrentLevel());
                        gp.win = false;
                    }
                    if (gp.ui.commandNum == 1) {
                        gp.gameState = gp.titleState;
                        gp.win = false;
                    }
                }
            } else {
                if (code == KeyEvent.VK_ENTER) {
                    gp.gameState = gp.titleState;
                    gp.win = false;
                }
            }
        }
    }

    @Override
    public void keyReleased (KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if(code == KeyEvent.VK_E) {
            bulletPressed = false;
        }
    }
}
