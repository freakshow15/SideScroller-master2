import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Hero extends Actor
{
    //private because only needs to be accessed in this class.
    private GreenfootImage original = new GreenfootImage( "Dashman.png");

    //Add the following variables here: y, ySpeed, smallUp, up, cannotJump, and lookingRight
    private int y = 0;
    private int ySpeed = 1;
    private int smallUp = -6;
    private int up = -20;
    private boolean cannotJump = false;
    private boolean lookingRight = true;

    /**
     * Create constructor to scale the original and jumping images, mirror the 
     * original image horizontally, and set the image of the hero to the original
     * variable
     * modifier is public because it needs to be accessed by other classes.
     * 
     * @param There are no parameters
     * @return Nothing is returned
     */
    public Hero()
    {
        original.scale(30, 30);
        original.mirrorHorizontally();
        setImage(original);
    }

    /**
     * Act - do whatever the Hero wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     * modifier is public because it needs to be accessed by other classes.
     * 
     * @param There are no parameters
     * @return Nothing is returned
     */
    public void act() 
    {

        // Add method call to movement method here
        movement();

        //Add method call to checkCollision here
        checkCollision();
    }

    /**
     * movement method that will handle the movement right, left, and up and mirror each respectively
     * also handles what happens when Space bar is pressed and allows hero to only shoot
     * certain amount of Fireballs for a given time
     * Modifier is private because it only needs to display the Text in this 
     * method
     * @param There are no parameters
     * @return Nothing is returned
     */
    private void movement()
    {
        GeometryDash myWorld = (GeometryDash)getWorld();
        if( Greenfoot.isKeyDown("right") )
        {

            if( lookingRight ==false)
            {
                original.mirrorHorizontally();

            }
            lookingRight = true;
            setLocation(getX()+3,getY());
        } 

        if( Greenfoot.isKeyDown("left") )
        {

            if( lookingRight ==true)
            {
                original.mirrorHorizontally();

            }

            lookingRight = false;
            setLocation(getX()-3,getY());
        }

        if( Greenfoot.isKeyDown("up") )
        {

            if( cannotJump == false)
            {
                y = up;
                fall();
            }
        } 

        if( getY() >= 360 )
        {
            setLocation(getX(),370);
            y = 0;
        }

    }

    /**
     * fall lets Hero class add GreenfootImage variables for original image and jumping image. 
     * Also adds variables y, ySpeed, smallUp, up, cannotJump, and lookingRight
     * Modifier is private because it only needs to display the Text in this 
     * method
     * 
     * @param There are no parameters
     * @return Nothing is returned
     */
    private void fall()
    {
        cannotJump = true;
        setLocation(getX(),getY()+y);
        y = y + ySpeed;
        setRotation(getRotation() + 11);
    }

    /**
     * --checkCollision method will check if we've landed on the top
     * of an Spike, which will increase the score; touched an Spike otherwise, which
     * will have us decrease the health by 100; touched a platform which will allow us to jump again;
     * or fall and also if the hero touches a Trophy increase its health by 
     * 25
     * Modifier is private because it only needs to display the Text in this 
     * method
     * 
     * @param There are no parameters
     * @return Nothing is returned
     */
    private void checkCollision()
    {
        GeometryDash myWorld = (GeometryDash)getWorld();
        HealthBar bar = getWorld().getObjects(HealthBar.class).get(0);
        if( isTouching(Obstacle.class) )
        {
            ((GeometryDash)getWorld()).gameOver();
        }

        else if( isTouching(Trophy.class) )
        {
            getWorld().removeObject(getOneIntersectingObject(Trophy.class) );
            ((GeometryDash)getWorld()).champion();
        }
        else if( isTouching(Block.class) )
        {
            if(getOneObjectAtOffset(1, 0, Block.class) != null)
            {   
                ((GeometryDash)getWorld()).gameOver();
            }
            else
            {        
                setImage(original);
                cannotJump = false;
                y = 0;
                setRotation(0);
            }
        }          
        else if( getOneObjectAtOffset(0, getImage().getHeight()-15, Platform.class) != null)
        {
            setImage(original);
            cannotJump = false;
            y = 0;
            setRotation(0);
        }
        else
        {
            fall();
        }

    }

    /**
     * getRight allows fireball to know direction of Hero and which way it is facing
     * modifier is public because it needs to be accessed by other classes.
     * 
     * @param There are no parameters
     * @return Direction Hero is looking
     */
    public boolean getRight()
    {
        return lookingRight;
    }
}
