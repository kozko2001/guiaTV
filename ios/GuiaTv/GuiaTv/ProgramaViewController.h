//
//  ProgramaViewController.h
//  GuiaTv
//
//  Created by Jordi Coscolla on 25/02/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ViewController.h"
#import "ProgramaModel.h"

@interface ProgramaViewController : UIViewController

@property   (nonatomic, strong) ProgramaModel *programa;
@property   (nonatomic, strong) IBOutlet UINavigationBar *navBar;
@property   (nonatomic, strong) IBOutlet UILabel     *categoria;
@property   (nonatomic, strong) IBOutlet UILabel     *votos;
@property   (nonatomic, strong) IBOutlet UIImageView *imagen;
@property   (nonatomic, strong) IBOutlet UITextView  *desc;

@end
