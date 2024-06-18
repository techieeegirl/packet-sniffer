package unRunAble.InterFaces;

import unRunAble.BackEnd;

/**
 * FrontEnd Interface for all places(Users) when it is to be used outside of the BackEnd
 * For Example:
 * When Main class needs to initialize the FrontEnd and set it visible
 */
public interface FrontEndInterface {
    void setBackEnd(BackEnd backEnd);

    void setVisible();
}
