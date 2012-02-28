//
//  ProgramaViewController.m
//  GuiaTv
//
//  Created by Jordi Coscolla on 25/02/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ProgramaViewController.h"

@interface ProgramaViewController ()

@end

@implementation ProgramaViewController

@synthesize programa, navBar, desc,categoria, imagen, votos;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    navBar.topItem.title = programa.title;

    NSLog(@"%@" , programa.categoria);
    NSLog(@"%@" , programa.imagen);
    
    self.desc.text = programa.desc;
    self.categoria.text = programa.categoria;
    self.votos.text = @"4.5 (150 votos";
    NSData* imageData = [[NSData alloc]initWithContentsOfURL:[NSURL URLWithString:  programa.imagen]];
    [self.imagen setImage: [[UIImage alloc] initWithData: imageData]];
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

@end
