//
//  ProgramacioViewController.m
//  GuiaTv
//
//  Created by Jordi Coscolla on 23/02/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ProgramacioViewController.h"
#import "ChannelModel.h"
#import "ProgramaModel.h"
#import "AppDelegate.h"
#import "PSStackedView.h"
#import "ProgramaViewController.h"
#import "ProgramacioModel.h"
@interface ProgramacioViewController ()

@end


@implementation ProgramacioViewController

@synthesize channel, programas = _programas, tvCell, tableView, navBar;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {

    }
    return self;
}

- (void)viewDidLoad
{
    _programas = [self.channel getProgramas];
    
    
    [super viewDidLoad];

}

- (void)viewDidUnload
{
    
    [super viewDidUnload];
    
    PSStackedViewController *stackController = XAppDelegate.stackController;
    if( detailView != nil)
        [stackController   popViewControllerAnimated:false];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
	return YES;
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView
{
    
    return [_programas numDias];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    // Return the number of rows in the section.
    return [_programas numProgramasByDia: section];
}

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
    return  [[NSString alloc] initWithFormat: @"%i" , section];
}


- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *CellIdentifier = @"Cell";
    UITableViewCell *cell = [self.tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        [[NSBundle mainBundle] loadNibNamed:@"ProgramacioCell" owner:self options:nil];
        cell = tvCell;
        self.tvCell = nil;
    }
    
    UILabel *title = (UILabel*) [cell viewWithTag: 1];
    UILabel *categoria = (UILabel*) [cell viewWithTag: 2];
    UILabel *hora = (UILabel*) [cell viewWithTag: 3];
    UILabel *desc  = (UILabel*) [cell viewWithTag: 4];
    

    ProgramaModel *programa = [self getProgramaByIndexPath:indexPath];
    
    title.text = [[NSString alloc] initWithFormat:@"%@" , programa.title];
    categoria.text = [[NSString alloc] initWithFormat:@"%@" , programa.categoria];
    hora.text= [[NSString alloc] initWithFormat:@"%i" , programa.start];
    desc.text = [[NSString alloc] initWithFormat:@"%@" , programa.desc];

    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    ProgramaModel *programa = [self getProgramaByIndexPath:indexPath];
    
    CGFloat r =  programa.end - programa.start;
    if ( r < 40 ) 
        return 40;
    if( r > 200) 
        return 200;
    return r;
}

-(ProgramaModel*) getProgramaByIndexPath: (NSIndexPath*) indexPath
{
    int section = indexPath.section;
    int row = indexPath.row;
    
    return [_programas getProgramaByDia:section AndRow: row];
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath
{
    PSStackedViewController *stackController = XAppDelegate.stackController;
    ProgramaModel *programa = [self getProgramaByIndexPath:indexPath];
    if( detailView != nil)
        [stackController   popViewControllerAnimated:false];
    
    ProgramaViewController *v = [[ProgramaViewController alloc] initWithNibName:@"ProgramaViewController" bundle:nil];
    v.programa = programa;
    detailView = v;
    
    [stackController pushViewController:v fromViewController:self animated:true];
}
@end
