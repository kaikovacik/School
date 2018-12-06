# Programming Languages, Homework 6, hw6runner.rb

# This is the only file you turn in,
# so do not modify the other files as
# part of your solution.

class MyPiece < Piece
  # The constant All_My_Pieces should be declared here:
  All_My_Pieces = [[[[0, 0], [1, 0], [0, 1], [1, 1]]],  # square (only needs one)
                  rotations([[0, 0], [-1, 0], [1, 0], [0, -1]]), # T
                  [[[0, 0], [-1, 0], [1, 0], [2, 0]], # long (only needs two)
                  [[0, 0], [0, -1], [0, 1], [0, 2]]],
                  rotations([[0, 0], [0, -1], [0, 1], [1, 1]]), # L
                  rotations([[0, 0], [0, -1], [0, 1], [-1, 1]]), # inverted L
                  rotations([[0, 0], [-1, 0], [0, -1], [1, -1]]), # S
                  rotations([[0, 0], [1, 0], [0, -1], [-1, -1]])], # Z
                  # [[[0, 0], [0, -1], [0, 1], [0, 2], [0, -2]], # additional piece 1 (only needs two)
                  # [[0, 0], [-1, 0], [1, 0], [2, 0], [-2, 0]]],
                  # rotations([[0, 0], [0, -1], [1, 0]]) # additional piece 2 
                  # rotations([[0, 0], [1, 0], [2, 0], [0, -1], [1, -1]]) # additional piece 3

  # Your Enhancements here:
  def self.next_piece (board)
    x = All_My_Pieces.sample
    print x
    # MyPiece.new(x, board)
  end

end

class MyBoard < Board
  # Your Enhancements here:
  def initialize (game)
    @grid = Array.new(num_rows) {Array.new(num_columns)}
    @current_block = MyPiece.next_piece(self)
    @score = 0
    @game = game
    @delay = 500
  end

end

class MyTetris < Tetris
  # Your Enhancements here:
  def initialize
    @root = TetrisRoot.new
    @timer = TetrisTimer.new
    set_board
    @running = true
    key_bindings
    buttons
    run_game
  end

  def set_board
    @canvas = TetrisCanvas.new
    @board = MyBoard.new(self)
    @canvas.place(@board.block_size * @board.num_rows + 3,
                  @board.block_size * @board.num_columns + 6, 24, 80)
    @board.draw
  end

end